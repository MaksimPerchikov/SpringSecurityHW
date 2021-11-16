package com.securityHW.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.securityHW.config.ApplicationUserRole.EMPLOYEE;
import static com.securityHW.config.ApplicationUserRole.MANAGER;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","index").permitAll()  //позволит открывать эти странички всем
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }
    @Bean
    @Override //создаем сотрудников
    protected UserDetailsService userDetailsService() {
        UserDetails oliverUser =
                User.builder()
                .username("oliver")
                .password(passwordEncoder.encode("password"))
                .roles(EMPLOYEE.name())
                .build();

        UserDetails henryUser =
                User.builder()
                        .username("henry")
                        .password(passwordEncoder.encode("password123"))
                        .roles(MANAGER.name())
                        .build();

        return new InMemoryUserDetailsManager(oliverUser,henryUser);
    }
}
