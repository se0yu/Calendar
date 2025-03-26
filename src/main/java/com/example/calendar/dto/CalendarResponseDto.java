package com.example.calendar.dto;

import com.example.calendar.entity.Calendar;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;


@Getter
@AllArgsConstructor
public class CalendarResponseDto {
    private Long id;
    private String todo;
    private String writer;
    //TimeStamp -> yyyy-mm-dd 형식의 String으로 반환
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp updatedAt;

    public CalendarResponseDto(Calendar calendar){
        this.id = calendar.getId();
        this.todo = calendar.getTodo();
        this.writer = calendar.getWriter();
        this.createdAt = calendar.getCreatedAt();
        this.updatedAt = calendar.getUpdatedAt();
    }


}
