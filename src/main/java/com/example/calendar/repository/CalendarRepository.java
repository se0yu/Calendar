package com.example.calendar.repository;

import com.example.calendar.dto.CalendarRequestDto;
import com.example.calendar.dto.CalendarResponseDto;
import com.example.calendar.entity.Calendar;

import java.sql.Timestamp;

public interface CalendarRepository {
    CalendarResponseDto saveTodo(Calendar calendar);

    String timeStampToString (Timestamp timestamp);
}
