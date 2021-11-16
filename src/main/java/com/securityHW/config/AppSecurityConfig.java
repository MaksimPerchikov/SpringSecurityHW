package com.securityHW.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.securityHW.config.ApplicationUserPermission.TASK_WRITE;
import static com.securityHW.config.ApplicationUserRole.*;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index").permitAll()  //позволит открывать эти странички всем
                .antMatchers("/manager/api/**").hasAnyRole(MANAGER.name(),SCRUM_MASTER.name()) //доступно только для манагера и скрама
                .antMatchers(HttpMethod.PUT,"api/task").hasAuthority(TASK_WRITE.getPermission()) //закрываем доступ к api/task.Можно указывать не только Паттер эдпоинта("api/task"), но и метода.
                //Резюмирую,то есть,чтобы вызывать этот метод Пут,нужно иметь права доступа к ТаскВрайт
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

        UserDetails maksimUser =
                User.builder()
                        .username("Maksim")
                        .password(passwordEncoder.encode("pass"))
                        .roles(SCRUM_MASTER.name())
                        .build();

        return new InMemoryUserDetailsManager(oliverUser,henryUser,maksimUser);
    }
}
