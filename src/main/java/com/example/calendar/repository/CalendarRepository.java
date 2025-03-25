package com.example.calendar.repository;

import com.example.calendar.dto.CalendarRequestDto;
import com.example.calendar.dto.CalendarResponseDto;
import com.example.calendar.entity.Calendar;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface CalendarRepository {

    String timeStampToString (Timestamp timestamp);

    CalendarResponseDto saveTodo(Calendar calendar);

    List<CalendarResponseDto> findAllTodo(String writer, String updatedAt);

    Calendar findTodoById(Long id);

    int updateCalendar(Long id, String todo, String writer, String password);
}
