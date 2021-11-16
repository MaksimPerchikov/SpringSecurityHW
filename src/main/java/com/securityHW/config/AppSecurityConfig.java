package com.securityHW;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

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
    @Override
    protected UserDetailsService userDetailsService() {]
        User.builder()
                .username("oliver")
                .password("password")

        return new InMemoryUserDetailsManager();
    }
}
