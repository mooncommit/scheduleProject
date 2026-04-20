package com.example.deepeningschedule.dto.schedule;

import com.example.deepeningschedule.entity.User;


public class CreateScheduleRequestDto {
    private String title;
    private String content;
    private User user;


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }
}

