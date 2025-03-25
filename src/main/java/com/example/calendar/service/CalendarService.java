package com.example.calendar.service;

import com.example.calendar.dto.CalendarRequestDto;
import com.example.calendar.dto.CalendarResponseDto;

import java.util.List;

public interface CalendarService {
    CalendarResponseDto saveTodo(CalendarRequestDto requestDto);

    List<CalendarResponseDto> findAllTodo(String writer, String updatedAt);

    CalendarResponseDto findTodoById(Long id);

    CalendarResponseDto updateCalendar(Long id, String todo, String writer, String password);

    void deleteTodo(Long id, String password);
}
