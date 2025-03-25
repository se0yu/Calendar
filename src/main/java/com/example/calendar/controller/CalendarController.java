package com.example.calendar.controller;

import com.example.calendar.dto.CalendarRequestDto;
import com.example.calendar.dto.CalendarResponseDto;
import com.example.calendar.service.CalendarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;

@RestController
@RequestMapping("/{calendars}")
public class CalendarController {
    private final CalendarService calendarService;


    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    //일정 추가 기능
    @PostMapping
    public ResponseEntity<CalendarResponseDto> createToto(@RequestBody CalendarRequestDto requestDto){

        return new ResponseEntity<>(calendarService.saveTodo(requestDto), HttpStatus.CREATED);
    }

    //일정 전체 조회
    @GetMapping
    public List<CalendarResponseDto> findAllTodo(
            @RequestParam(required = false) String writer,
            @RequestParam(required = false) String updatedAt
    ){

        return calendarService.findAllTodo(writer,updatedAt);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarResponseDto> findTodoById(@PathVariable Long id){
        return new ResponseEntity<>(calendarService.findTodoById(id), HttpStatus.OK);
    }

}
