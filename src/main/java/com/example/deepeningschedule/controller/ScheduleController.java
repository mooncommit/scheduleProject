package com.example.deepeningschedule.controller;

import com.example.deepeningschedule.dto.schedule.*;
import com.example.deepeningschedule.service.ScheduleService;
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

    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(@RequestBody CreateScheduleRequestDto request) {
        CreateScheduleResponseDto responseDto = scheduleService.save(request);
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

    @PutMapping("/{id}")
    public ResponseEntity<UpdateScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody UpdateScheduleRequestDto result) {
        UpdateScheduleResponseDto responseDto = scheduleService.updateSchedule(id, result);
        return ResponseEntity.ok(responseDto);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok().build();
    }
}
