package com.securityHW.jwt;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtTokVerif extends OncePerRequestFilter {


    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
            String tokenFirst = jwtProvider.resolveToken(request);

            if(tokenFirst == null){
                filterChain.doFilter(request,response);
                return;
            }
            String username = jwtProvider.getUsername(tokenFirst);
            List<GrantedAuthority> authorities = jwtProvider.getAuthorities(tokenFirst);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username,null,authorities));
        }catch (JwtException e){
            throw new IllegalArgumentException("Token is not valid");
        }
        filterChain.doFilter(request,response);
    }
}
