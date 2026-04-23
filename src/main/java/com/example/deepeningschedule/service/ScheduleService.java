package com.example.deepeningschedule.service;

import com.example.deepeningschedule.dto.schedule.*;
import com.example.deepeningschedule.entity.Schedule;
import com.example.deepeningschedule.entity.User;
import com.example.deepeningschedule.repository.ScheduleRepository;
import com.example.deepeningschedule.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateScheduleResponseDto save(CreateScheduleRequestDto request, User user) {
        // 1. userId 찾기 (없으면 예외처리)
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                user
        );

        Schedule savedSchedules = scheduleRepository.save(schedule);

        CreateScheduleResponseDto responseDto = new CreateScheduleResponseDto(
                savedSchedules.getId(),
                savedSchedules.getTitle(),
                savedSchedules.getContent(),
                savedSchedules.getUser().getId(),
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
                        schedule.getUser().getId(),
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
                schedule.getUser().getId(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
        return responseDto;
    }

    // 수정
    @Transactional
    public UpdateScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto result, User user) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 일정이 없습니다."));
        // 로그인 한 유저와 같은 지 확인
        if (!user.getEmail().equals(schedule.getUser().getEmail())) {
            throw new RuntimeException("본인 일정만 수정할 수 있습니다.");
        }
        schedule.update(result);
        UpdateScheduleResponseDto responseDto = new UpdateScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getId(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
        return responseDto;
    }

    // 삭제
    @Transactional
    public void deleteSchedule(Long id, User user) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("삭제할 일정이 없습니다."));
        if (!user.getEmail().equals(schedule.getUser().getEmail())) {
            throw new RuntimeException("본인 일정만 삭제할 수 있습니다.");
        }
        scheduleRepository.delete(schedule);
    }
}
