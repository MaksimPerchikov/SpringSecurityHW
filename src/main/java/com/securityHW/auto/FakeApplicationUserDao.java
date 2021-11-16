package com.securityHW.auto;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.securityHW.config.ApplicationUserRole.*;

@RequiredArgsConstructor
@Service("fake")
public class FakeApplicationUserDao implements ApplicationUserDao {
    // имплементация юзер дао, реализация фейковой базы

    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> selectUserFromDbByUserName(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

// Создали модель АппликейшенЮзер,в которой мы храним наши данные, она(ApplicationUser) реализует ЮзерДетаилс,которая содержит
    // все необходимы методы
    private List<ApplicationUser> getApplicationUsers() {
        return Lists.newArrayList(
                new ApplicationUser(
                        "oliver",
                        passwordEncoder.encode("password"),
                        EMPLOYEE.getAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "henry",
                        passwordEncoder.encode("password123"),
                        MANAGER.getAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "emma",
                        passwordEncoder.encode("pass"),
                        TRAINEE.getAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                "maksim",
                        passwordEncoder.encode("pass"),
                        SCRUM_MASTER.getAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        );
    }
}
