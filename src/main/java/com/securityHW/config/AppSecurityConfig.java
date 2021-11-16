package com.securityHW.config;

import com.securityHW.auto.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilter(new UsernamePasswordAuthenticationFilter())
                .authorizeRequests()
                .antMatchers("/","index").permitAll()  //позволит открывать эти странички всем
       //         .antMatchers(HttpMethod.DELETE).hasAuthority(MANAGER.name())
        //        .antMatchers("/manager/api/**").hasAnyRole(MANAGER.name(),SCRUM_MASTER.name()) //доступно только для манагера и скрама
        //        .antMatchers(HttpMethod.PUT,"/api/task/**").hasAuthority(TASK_WRITE.getPermission()) //закрываем доступ к api/task.Можно указывать не только Паттер эдпоинта("api/task"), но и метода.
        //        //Резюмирую,то есть,чтобы вызывать этот метод Пут,нужно иметь права доступа к ТаскВрайт
        //        .antMatchers("/api/task/**").hasAnyRole(EMPLOYEE.name(), TRAINEE.name())//доступно только сотрудника и медеджера
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
    /*   @Bean
    @Override //создаем сотрудников
    protected UserDetailsService userDetailsService() {
        UserDetails oliverUser =
                User.builder()
                .username("oliver")
                .password(passwordEncoder.encode("password"))
                .authorities(EMPLOYEE.getAuthorities())
                .build();

        UserDetails henryUser =
                User.builder()
                        .username("henry")
                        .password(passwordEncoder.encode("password123"))
                        .authorities(MANAGER.getAuthorities())
                        .build();

        UserDetails maksimUser =
                User.builder()
                        .username("maksim")
                        .password(passwordEncoder.encode("pass"))
                        .authorities(SCRUM_MASTER.getAuthorities())
                        .build();

        UserDetails emmaUser =
                User.builder()
                        .username("emma")
                        .password(passwordEncoder.encode("pass"))
                        .authorities(TRAINEE.getAuthorities())
                        .build();

        return new InMemoryUserDetailsManager(oliverUser,henryUser,maksimUser,emmaUser);
    }*/
}
