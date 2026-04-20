package com.example.deepeningschedule.dto;

import java.time.LocalDateTime;

public class CreateScheduleRequestDto {
    private String title;
    private String content;
    private String author;


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}

