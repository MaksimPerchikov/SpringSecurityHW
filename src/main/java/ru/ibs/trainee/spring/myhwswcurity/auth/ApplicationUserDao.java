package ru.ibs.trainee.spring.myhwswcurity.auth;

import java.util.Optional;


//возвращает ЮзерДетэйлс, отвечает за хранилище
public interface ApplicationUserDao {

    Optional<ApplicationUser> selectUserFromDbByUserName(String username);
}
