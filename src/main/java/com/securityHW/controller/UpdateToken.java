
package com.securityHW.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/update/token")
public class UpdateToken{

    @PreAuthorize("hasRole('SCRUM_MASTER')")
    @GetMapping
    public void updateToken(){





    }


}

