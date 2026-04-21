package com.example.deepeningschedule.controller;

import com.example.deepeningschedule.dto.schedule.GetAllSchedulesResponseDto;
import com.example.deepeningschedule.dto.schedule.GetOneScheduleResponseDto;
import com.example.deepeningschedule.dto.user.*;
import com.example.deepeningschedule.service.UserService;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<GetAllUserResponseDto>> getAllUsers(ServletRequest servletRequest) {
        // 1. service에서 전체 유저 목록 가져오기
        List<GetAllUserResponseDto> responseDtoList = userService.getAllUsers();
        // 2. 반환 객체(List) 만들기
        ResponseEntity<List<GetAllUserResponseDto>> response = new ResponseEntity<>(responseDtoList, HttpStatus.OK);
        // 3. 반환하기
        return response;
    }

    // 단 건 조회
    @GetMapping("/{id}")
    public ResponseEntity<GetOneUserResponseDto> getOneUser(@PathVariable Long id) {
        // 1. service에서 id에 해당하는 유저 가져오기
        GetOneUserResponseDto responseDto = userService.getOneUser(id);
        // 2. 반환 객체 만들기
        ResponseEntity<GetOneUserResponseDto> response = new ResponseEntity<>(responseDto, HttpStatus.OK);
        // 3. 반환
        return response;
    }

    // 유저 수정
    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserResponseDto> updateUser(@PathVariable Long id,
                                                            @RequestBody UpdateUserRequestDto result) {
        // 1. service에서 업데이트한 유저 가져오기
        UpdateUserResponseDto responseDto = userService.updateUser(id, result);
        // 2. 반환 객체 만들기
        ResponseEntity<UpdateUserResponseDto> response = new ResponseEntity<>(responseDto, HttpStatus.OK);
        // 3. 반환
        return response;
    }
}
