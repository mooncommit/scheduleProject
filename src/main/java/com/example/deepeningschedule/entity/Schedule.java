package com.example.deepeningschedule.entity;

import com.example.deepeningschedule.dto.UpdateScheduleRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String author;

    public Schedule(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public void update(UpdateScheduleRequestDto result) {
        this.title = result.getTitle();
        this.content = result.getContent();
        this.author = result.getAuthor();

    }
}
