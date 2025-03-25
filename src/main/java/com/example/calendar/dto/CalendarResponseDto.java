package com.example.calendar.dto;

import com.example.calendar.entity.Calendar;
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

    public CalendarResponseDto(Calendar calendar){
        this.id = calendar.getId();
        this.todo = calendar.getTodo();
        this.writer = calendar.getWriter();
        this.createdAt = calendar.getCreatedAt();
        this.updatedAt = calendar.getUpdatedAt();
    }


}
