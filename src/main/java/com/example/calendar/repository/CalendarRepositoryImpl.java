package com.example.calendar.repository;

import com.example.calendar.dto.CalendarResponseDto;
import com.example.calendar.entity.Calendar;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@Repository
public class CalendarRepositoryImpl implements CalendarRepository{

    private final JdbcTemplate jdbcTemplate;

    public CalendarRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public CalendarResponseDto saveTodo(Calendar calendar) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("calendar").usingGeneratedKeyColumns("id");

        //작성 시간(현재 시간) 받아오기
        Timestamp timestampNow = Timestamp.valueOf(LocalDateTime.now());

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("todo", calendar.getTodo());
        parameters.put("writer", calendar.getWriter());
        parameters.put("password", calendar.getPassword());
        parameters.put("createdAt", timestampNow);
        parameters.put("updatedAt", timestampNow);

        //key값 long으로 변환
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        String updatedAt = timeStampToString(timestampNow);

        return new CalendarResponseDto(key.longValue(),calendar.getTodo(),calendar.getWriter(), updatedAt, updatedAt);

    }

    //DB에 저장된 TIMESTAMP -> String(yyyy-MM-dd)로 변환
    @Override
    public String timeStampToString(Timestamp timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return timestamp.toLocalDateTime().format(formatter);
    }
}
