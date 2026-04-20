package com.example.deepeningschedule.repository;

import com.example.deepeningschedule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
