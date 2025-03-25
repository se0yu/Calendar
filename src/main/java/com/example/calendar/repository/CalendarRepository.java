package com.example.calendar.repository;

import com.example.calendar.dto.CalendarRequestDto;
import com.example.calendar.dto.CalendarResponseDto;
import com.example.calendar.entity.Calendar;

import java.sql.Timestamp;
import java.util.List;

public interface CalendarRepository {

    String timeStampToString (Timestamp timestamp);

    CalendarResponseDto saveTodo(Calendar calendar);

    List<CalendarResponseDto> findAllTodo();


}
