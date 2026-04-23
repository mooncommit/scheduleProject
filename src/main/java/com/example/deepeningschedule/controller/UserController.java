package com.example.deepeningschedule.controller;

import com.example.deepeningschedule.dto.login.LoginRequestDto;
import com.example.deepeningschedule.dto.user.*;
import com.example.deepeningschedule.entity.User;
import com.example.deepeningschedule.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<CreateUserResponseDto> createUser(@RequestBody CreateUserRequestDto request) {
        // service에서 save 메서드 호출해서 response에 담기
        CreateUserResponseDto responseDto = userService.save(request);

        // 반환객체 만들기
        ResponseEntity<CreateUserResponseDto> response = new ResponseEntity<>(responseDto, HttpStatus.CREATED);

        // 반환
        return response;
    }

    @GetMapping
    public ResponseEntity<List<GetAllUserResponseDto>> getAllUsers() {
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

    // 유저 수정 ㅇ
    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserResponseDto> updateUser(
            @PathVariable Long id, @RequestBody UpdateUserRequestDto result, HttpServletRequest servletRequest) {
        // 세션 저장
        HttpSession session = servletRequest.getSession(false);
        if (session == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        User user = (User) session.getAttribute("loginUser");
        // 1. service에서 업데이트한 유저 가져오기
        UpdateUserResponseDto responseDto = userService.updateUser(id, result, user);
        // 2. 반환 객체 만들기
        ResponseEntity<UpdateUserResponseDto> response = new ResponseEntity<>(responseDto, HttpStatus.OK);
        // 3. 반환
        return response;
    }

    // 삭제 ㅇ
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession(false);
        if (session == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        User user = (User) session.getAttribute("loginUser");
        // 1. service에서 id에 해당하는 유저 삭제 요청
        userService.delectUser(id, user);
        // 2. 반환 객체 만들기
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);
        // 3. 반환
        return response;
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto request,
                                      HttpServletRequest servletRequest) {
        // 1. service에서 찾은 유저 가져오기
        User user = userService.login(request);
        // 유저를 세션에 저장해주는 과정
        // 2. 세션 가져오기 (없으면 새로 생성)
        HttpSession session = servletRequest.getSession();
        // 3. 세션 저장하기
        session.setAttribute("loginUser", user); // 키, 값
        // 4. 반환 객체 만들기
        ResponseEntity<String> response = new ResponseEntity<>("로그인 성공", HttpStatus.OK);
        // 5. 반환
        return response;

        // servletRequest.getSession(); 디폴드 값
        // servletRequest.getSession(false);

        // 쿠키는 어디있을까?
        // servletRequest.getSession()를 호출하면 spring이 자동으로
        // 세션 id를 쿠키에 담아서 브라우저에 전달한. (직접 다룰 필요 없음)
    }
}
