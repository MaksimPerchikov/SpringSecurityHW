package com.securityHW.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager manager;

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




        //нам надо аутентифицировать
    }
}
