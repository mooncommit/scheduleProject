package com.example.deepeningschedule.service;

import com.example.deepeningschedule.dto.schedule.*;
import com.example.deepeningschedule.entity.Schedule;
import com.example.deepeningschedule.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponseDto save(CreateScheduleRequestDto request) {
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                request.getUser()
        );

        Schedule savedSchedules = scheduleRepository.save(schedule);

        CreateScheduleResponseDto responseDto = new CreateScheduleResponseDto(
                savedSchedules.getId(),
                savedSchedules.getTitle(),
                savedSchedules.getContent(),
                savedSchedules.getUser(),
                savedSchedules.getCreatedAt(),
                savedSchedules.getModifiedAt()
        );
        return responseDto;
    }

    // 전체 조회 메서드
    @Transactional(readOnly = true)
    public List<GetAllSchedulesResponseDto> getAllSchedules() {
        // 1. 전체 일정 가져오기
        List<Schedule> schedules = scheduleRepository.findAll();

        // 2. DTO로 변환(stream 사용)
        List<GetAllSchedulesResponseDto> result = schedules.stream()
                .map(schedule -> new GetAllSchedulesResponseDto(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getUser(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                ))
                .collect(Collectors.toList());
        return result;
    }

    // 단 건 조회
    @Transactional(readOnly = true)
    public GetOneScheduleResponseDto getOneSchedule(Long id) {
//        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
//        if (optionalSchedule.isEmpty()) {
//            throw new EntityNotFoundException();
//        }
//        Schedule schedule = optionalSchedule.get();
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        GetOneScheduleResponseDto responseDto = new GetOneScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
        return responseDto;
    }

    // 수정
    @Transactional
    public UpdateScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto result) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 일정이 없습니다."));
        schedule.update(result);
        UpdateScheduleResponseDto responseDto = new UpdateScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
        return responseDto;
    }

    // 삭제
    @Transactional
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("삭제할 일정이 없습니다."));
        scheduleRepository.delete(schedule);
    }
}
