package com.example.calendar.service;

import com.example.calendar.dto.CalendarRequestDto;
import com.example.calendar.dto.CalendarResponseDto;
import com.example.calendar.entity.Calendar;
import com.example.calendar.repository.CalendarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService{

    private final CalendarRepository calendarRepository;

    public CalendarServiceImpl(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    //일정 저장
    @Override
    public CalendarResponseDto saveTodo(CalendarRequestDto requestDto) {
        Calendar calendar = new Calendar(requestDto.getTodo(), requestDto.getWriter(), requestDto.getPassword());

        return calendarRepository.saveTodo(calendar);
    }

    @Override
    public List<CalendarResponseDto> findAllTodo(String writer, String updatedAt) {
        return calendarRepository.findAllTodo(writer, updatedAt);
    }

}
