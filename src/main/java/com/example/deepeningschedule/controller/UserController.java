package com.example.deepeningschedule.controller;

import com.example.deepeningschedule.dto.user.CreateUserRequestDto;
import com.example.deepeningschedule.dto.user.CreateUserResponseDto;
import com.example.deepeningschedule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<CreateUserResponseDto> createUser(@RequestBody CreateUserRequestDto result) {
        // service에서 save 메서드 호출해서 response에 담기
        CreateUserResponseDto responseDto = userService.save(result);

        // 반환객체 만들기
        ResponseEntity<CreateUserResponseDto> response = new ResponseEntity<>(responseDto, HttpStatus.CREATED);

        // 반환
        return response;

    }
}
