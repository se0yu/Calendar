package com.example.calendar.repository;

import com.example.calendar.dto.CalendarResponseDto;
import com.example.calendar.entity.Calendar;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class CalendarRepositoryImpl implements CalendarRepository{

    private final JdbcTemplate jdbcTemplate;

    public CalendarRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //새 일정 추가
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

    //일정 목록 전체 조회
    @Override
    public List<CalendarResponseDto> findAllTodo(String writer, String updatedAt) {
        //password값을 제외한 데이터 출력(updateAt 기준으로 내림차순 정렬)
//        parameter로 writer, updatedAt 입력 받아 특정 일정만 출력하기
//        String writerForSql = "'%" + writer + "%'";
//        String updatedAtForSql = "'" + updatedAt + "'";
//        String sql = """
//        select id, todo, writer, createdAt, updatedAt
//        from calendar
//        where writer like :writerForSql and date(updatedAt) = date(:updatedAtForSql)
//        order by updatedAt desc
//        """;
//
//
//        select id, todo, writer, createdAt, updatedAt
//        from calendar
//        where (:writer is null or writer like :writerForSql)
//        and (:updatedAt is null or date(updatedAt) = date(:updatedAtForSql))
//        order by updatedAt desc

        return jdbcTemplate.query("select id, todo, writer, createdAt, updatedAt from calendar order by updatedAt desc",calendarRowMapper());
    }

    //일정 단일 조회 Pathvariable = id
    @Override
    public Calendar findTodoById(Long id) {
        List<Calendar> todoList = jdbcTemplate.query("select * from calendar where id = ? order by updatedAt desc",calendarRowMapperByCalendar(),id);
        return todoList.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exist id = " + id));
    }

    //일정 수정
    @Override
    public int updateCalendar(Long id, String todo, String writer, String password) {
        String savedPassword = jdbcTemplate.queryForObject("select password from calendar where id = ?",String.class, id);
        if(!savedPassword.equals(password)){
            return 0;
        }
        int updatedCalendar = jdbcTemplate.update("update calendar set todo = ?, writer = ?, updatedAt = now() where id = ? and password = ? ", todo, writer, id, password);

        return updatedCalendar;
    }

    @Override
    public int deleteTodo(Long id, String password) {
        String savedPassword = jdbcTemplate.queryForObject("select password from calendar where id = ?",String.class, id);
        if(!savedPassword.equals(password)){
            return 0;
        }
        return jdbcTemplate.update("delete from calendar where id = ?", id);
    }

    //DB에 저장된 TIMESTAMP -> String(yyyy-MM-dd)로 변환
    @Override
    public String timeStampToString(Timestamp timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return timestamp.toLocalDateTime().format(formatter);
    }

    private RowMapper<CalendarResponseDto> calendarRowMapper (){
            return new RowMapper<CalendarResponseDto>() {
                @Override
                public CalendarResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new CalendarResponseDto(
                            rs.getLong("id"),
                            rs.getString("todo"),
                            rs.getString("writer"),
                            timeStampToString(rs.getTimestamp("createdAt")),
                            timeStampToString(rs.getTimestamp("updatedAt"))
                    );
                }
            };
    }

    private RowMapper<Calendar> calendarRowMapperByCalendar(){
        return new RowMapper<Calendar>() {
            @Override
            public Calendar mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Calendar(
                        rs.getLong("id"),
                        rs.getString("todo"),
                        rs.getString("writer"),
                        rs.getString("password"),
                        timeStampToString(rs.getTimestamp("createdAt")),
                        timeStampToString(rs.getTimestamp("updatedAt"))
                );
            }
        };
    }
}
