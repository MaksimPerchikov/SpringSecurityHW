package com.securityHW.auto;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplicationUserService implements UserDetailsService {

    @Qualifier("fake")
    private final ApplicationUserDao applicationUserDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //здесь мы будем получать информацию о пользователсях
        //доставем пользователя из базы данных, для этого есть интерфейс ApplicationUserDao
        return applicationUserDao.selectUserFromDbByUserName(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
