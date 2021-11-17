package com.securityHW.controller;

import com.securityHW.jwt.JwtProvider;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/update/token")
public class UpdateToken  {

    private final JwtProvider jwtProvider;
    private final AuthenticationManager manager;

    public UpdateToken(JwtProvider jwtProvider, AuthenticationManager manager) {
        this.jwtProvider = jwtProvider;
        this.manager = manager;
    }

    @GetMapping("{token}")
    public void create(@PathVariable("token") HttpRequest token){

        try{
            String tokenFirst = jwtProvider.resolveToken(token);

            String username = jwtProvider.getUsername(tokenFirst);
            List<GrantedAuthority> authorities = jwtProvider.getAuthorities(tokenFirst);

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(username,
                    null,authorities));
        }catch (JwtException e){
            throw new IllegalArgumentException("Token is not valid");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(token);

       // Authentication authenticate = manager.authenticate(authentication);
       // String tokenFirst = jwtProvider.createToken();

    }




}
