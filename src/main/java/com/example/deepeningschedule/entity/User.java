package com.example.deepeningschedule.entity;

import com.example.deepeningschedule.dto.user.UpdateUserRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;

    // 비밀번호 필드 추가
    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() { return password; }

    // update 기능
    public User update(String username, String email, String newPassword) {
        this.username = username;
        this.email = email;
        this.password = newPassword;
        return this;
    }
}
