
package com.securityHW.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securityHW.jwt.JwtProvider;
import com.securityHW.jwt.UsernamePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/update/token")
public class UpdateToken{

    AuthenticationManager manager;
    @Autowired
    HttpServletRequest request;
    JwtProvider jwtProvider;

    @PreAuthorize("hasRole('SCRUM_MASTER')")
    @GetMapping
    public Map<String,String> updateToken() throws IOException {

        UsernamePasswordRequest usernamePasswordRequest = new ObjectMapper() // это мы получаем модельку из запроса
                .readValue(request
                        .getInputStream(),UsernamePasswordRequest.class);

        Authentication authentication = new UsernamePasswordAuthenticationToken(usernamePasswordRequest.getUsername(),
                usernamePasswordRequest.getPassword());

        Authentication authenticate =
                manager.authenticate(authentication);

        String tokenFirstNew = jwtProvider.createToken(authentication);
        String tokenRefreshNew = jwtProvider.createRefreshToken(authentication);


        Map<String,String > mapToken = new HashMap<>();
        mapToken.put("first token new",tokenFirstNew);
        mapToken.put("first refresh new",tokenRefreshNew);
        /*objectMapper.writeValue(response.getOutputStream(), listToken.listIterator());*/ //Вывод через лист
        return mapToken;
    }


}

