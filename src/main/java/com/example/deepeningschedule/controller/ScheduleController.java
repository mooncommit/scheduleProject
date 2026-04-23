package com.example.deepeningschedule.controller;

import com.example.deepeningschedule.dto.schedule.*;
import com.example.deepeningschedule.entity.User;
import com.example.deepeningschedule.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 일정 생성 ㅇ
    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(@RequestBody CreateScheduleRequestDto request,
                                                                    HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        if (session == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        User user = (User) session.getAttribute("loginUser");
        CreateScheduleResponseDto responseDto = scheduleService.save(request, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 전체 일정 조회
    @GetMapping
    public ResponseEntity<List<GetAllSchedulesResponseDto>> getAllSchedules() {
        List<GetAllSchedulesResponseDto> responseDto = scheduleService.getAllSchedules();
        return ResponseEntity.ok(responseDto);
    }

    // 단 건 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<GetOneScheduleResponseDto> getOneSchedule(@PathVariable Long id) {
        GetOneScheduleResponseDto responseDto = scheduleService.getOneSchedule(id);
        return ResponseEntity.ok(responseDto);
    }

    // 수정 ㅇ
    @PutMapping("/{id}")
    public ResponseEntity<UpdateScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody UpdateScheduleRequestDto result,
            HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession(false);
        if (session == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        User user = (User) session.getAttribute("loginUser");
        UpdateScheduleResponseDto responseDto = scheduleService.updateSchedule(id, result, user);
        return ResponseEntity.ok(responseDto);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession(false);
        if (session == null) {
            throw new RuntimeException("로그인이 필요합니다");
        }
        User user = (User) session.getAttribute("loginUser");
        scheduleService.deleteSchedule(id, user);
        return ResponseEntity.ok("삭제 완료");
    }
}
