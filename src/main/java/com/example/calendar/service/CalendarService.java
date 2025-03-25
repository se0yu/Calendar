package com.example.calendar.service;

import com.example.calendar.dto.CalendarRequestDto;
import com.example.calendar.dto.CalendarResponseDto;

public interface CalendarService {
    CalendarResponseDto saveTodo(CalendarRequestDto requestDto);
}
