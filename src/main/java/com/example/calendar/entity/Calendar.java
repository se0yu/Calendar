package com.example.calendar.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Calendar {
    private Long id;
    private String todo;
    private String writer;
    private String password;
    private String createdAt;
    private String updatedAt;

    public Calendar(String todo, String writer, String password) {
        this.todo = todo;
        this.writer = writer;
        this.password = password;
    }


}
