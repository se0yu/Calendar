package com.example.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CalendarResponseDto {
    private Long id;
    private String todo;
    private String writer;
    private String createdAt;
    private String updatedAt;
}
