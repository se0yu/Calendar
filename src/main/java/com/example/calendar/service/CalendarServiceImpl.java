package com.example.calendar.service;

import com.example.calendar.dto.CalendarRequestDto;
import com.example.calendar.dto.CalendarResponseDto;
import com.example.calendar.entity.Calendar;
import com.example.calendar.repository.CalendarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public CalendarResponseDto findTodoById(Long id) {

        Calendar calendar = calendarRepository.findTodoById(id);
        return new CalendarResponseDto(calendar);
    }

    @Override
    public CalendarResponseDto updateTodo(Long id, String todo, String writer, String password ) {

        int updateRow = calendarRepository.updateTodo(id, todo, writer, password);
        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "올바른 일정, 혹은 비밀번호인지 확인해주세요." + id);
        }
        Calendar calendar = calendarRepository.findTodoById(id);
        return new CalendarResponseDto(calendar);
    }

    @Override
    public void deleteTodo(Long id, String password) {
        int deleteRow = calendarRepository.deleteTodo(id,password);

        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정 혹은 비밀번호를 다시 확인해 주세요. " + id);
        }
    }


}
