package com.example.deepeningschedule.dto.user;

import org.springframework.orm.jpa.persistenceunit.SpringPersistenceUnitInfo;

public class UpdateUserRequestDto {
    private String username;
    private String email;
    private String password;
    private String newPassword;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
