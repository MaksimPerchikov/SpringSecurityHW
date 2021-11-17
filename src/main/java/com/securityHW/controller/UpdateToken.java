package com.securityHW.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/update/token")
public class UpdateToken extends UsernamePasswordAuthenticationFilter {

    @PreAuthorize("hasRole('SCRUM_MASTER')")
    @GetMapping
    public void updateToken(){/*(HttpServletResponse response, Authentication authResult) throws IOException{
        JwtProvider jwtProvider = new JwtProvider();
        ObjectMapper objectMapper = new ObjectMapper();

        String tokenFirst = jwtProvider.createToken(authResult);
        String tokenRefresh = jwtProvider.createRefreshToken(authResult);

        Map<String, String> mapToken = new HashMap<>();
        mapToken.put("first token", tokenFirst);
        mapToken.put("first refresh", tokenRefresh);

        response.setContentType("application/json");

        objectMapper.writeValue(response.getOutputStream(), mapToken);*/


    }


}
