package com.securityHW.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager manager;
    private final JwtProvider jwtProvider;
   ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            UsernamePasswordRequest usernamePasswordRequest = new ObjectMapper() // это мы получаем модельку из запроса
                    .readValue(request
                    .getInputStream(),UsernamePasswordRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(usernamePasswordRequest.getUsername(),
                    usernamePasswordRequest.getPassword()); //сюда мы передаем полученный нами рекьюест (usernamePasswordRequest)


            //теперь аутентифицируем
            Authentication authenticate =
                    manager.authenticate(authentication); //мы должны аутентифицировать пользователя и помещаем в данный метод Аутентификецшн(Authentication)

            return authenticate;

        } catch (IOException e) {
            log.error("Unexpected error", e);
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String tokenFirst = jwtProvider.createToken(authResult);
        String tokenRefresh = jwtProvider.createRefreshToken(authResult);

        List<String> listToken = new ArrayList<>();
        listToken.add(tokenFirst);
        listToken.add(tokenRefresh);

        Map<String,String > mapToken = new HashMap<>();
        mapToken.put("first token",tokenFirst);
        mapToken.put("first refresh",tokenRefresh);

        response.setContentType("application/json");
        /*objectMapper.writeValue(response.getOutputStream(), listToken.listIterator());*/ //Вывод через лист
        objectMapper.writeValue(response.getOutputStream(),mapToken); //вывод через мапу

    }

/*
    eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWtzaW0iLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9TQ1JVTV9NQVNURVIifSx7ImF1dGhvcml0eSI6InRhc2s6d3JpdGUifSx7ImF1dGhvcml0eSI6ImVtcGxveWVlOnJlYWQifV0sImlhdCI6MTYzNzEwOTcwNiwiZXhwIjoxNjM3NzAxMjAwfQ.GCLavMDNDl6lKH60hqbFWHmnyiqJVC659nr8jS4JKYw


    eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZW5yeSIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJ0YXNrOnJlYWQifSx7ImF1dGhvcml0eSI6ImVtcGxveWVlOndyaXRlIn0seyJhdXRob3JpdHkiOiJ0YXNrOndyaXRlIn0seyJhdXRob3JpdHkiOiJST0xFX01BTkFHRVIifSx7ImF1dGhvcml0eSI6ImVtcGxveWVlOnJlYWQifV0sImlhdCI6MTYzNzExMTMxNSwiZXhwIjoxNjM3NzAxMjAwfQ.tls69AGI-Eis0rYfkP_sEcdLged9dz9fUcWgtE5jW50

*/
}
