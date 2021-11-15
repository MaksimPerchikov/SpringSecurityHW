package ru.ibs.trainee.spring.myhwswcurity.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Реализация ЮзерДетэйлСервис
//отвечает за получение пользователя из хранилища
@RequiredArgsConstructor
@Service
public class ApplicationUserService implements UserDetailsService {


    private final ApplicationUserDao applicationUserDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDao.selectUserFromDbByUserName(username)          //получаем информацию о пользователе
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));//если не нашли - исключение
    }
}
