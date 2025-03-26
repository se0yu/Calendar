package com.example.calendar.repository;

import com.example.calendar.dto.CalendarResponseDto;
import com.example.calendar.entity.Calendar;

import java.util.List;

public interface CalendarRepository {


    CalendarResponseDto saveTodo(Calendar calendar);

    List<CalendarResponseDto> findAllTodo(String writer, String updatedAt);

    Calendar findTodoById(Long id);

    int updateTodo(Long id, String todo, String writer, String password);

    int deleteTodo(Long id, String password);
}
