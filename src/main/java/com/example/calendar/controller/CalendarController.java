package com.example.calendar.controller;

import com.example.calendar.dto.CalendarRequestDto;
import com.example.calendar.dto.CalendarResponseDto;
import com.example.calendar.service.CalendarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{calendars}")
public class CalendarController {
    private final CalendarService calendarService;


    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @PostMapping
    public ResponseEntity<CalendarResponseDto> createToto(@RequestBody CalendarRequestDto requestDto){

        return new ResponseEntity<>(calendarService.saveTodo(requestDto), HttpStatus.CREATED);
    }

}
