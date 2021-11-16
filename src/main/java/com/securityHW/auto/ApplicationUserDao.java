package com.securityHW.auto;


import java.util.Optional;

//Интерфейс,который будет возвращать UserDetails
//Отвечает за хранилище
public interface ApplicationUserDao {

    Optional<ApplicationUser> selectUserFromDbByUserName(String username);

}
