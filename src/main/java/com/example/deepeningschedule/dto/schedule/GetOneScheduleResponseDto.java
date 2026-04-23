package com.example.deepeningschedule.dto.schedule;

import com.example.deepeningschedule.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetOneScheduleResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final Long userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetOneScheduleResponseDto(Long id, String title, String content, Long userId, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
