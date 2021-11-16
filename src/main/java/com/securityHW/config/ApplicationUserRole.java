package com.securityHW.config;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.securityHW.config.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    EMPLOYEE(Sets.newHashSet(EMPLOYEE_READ,TASK_READ,TASK_WRITE)),
    MANAGER(Sets.newHashSet(EMPLOYEE_READ,EMPLOYEE_WRITE,TASK_READ,TASK_WRITE)),
    ;
    // TRAINEE(Sets.newHashSet(EMPLOYEE_READ,TASK_READ)),
   // SCRUM_MASTER(Sets.newHashSet(TASK_WRITE,EMPLOYEE_READ))

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
