package com.example.demo.domain.member.reporsitory;

import com.example.demo.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final String TABLE_NAME = "member";
    private static final RowMapper<Member> rowMapper = (ResultSet resultSet, int rowNum) -> Member.builder()
                                                                                                  .id(resultSet.getLong("id"))
                                                                                                  .email(resultSet.getString("email"))
                                                                                                  .nickname(resultSet.getString("nickname"))
                                                                                                  .birthdate(resultSet.getObject("birthdate", LocalDate.class))
                                                                                                  .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
                                                                                                  .build();
    public Optional<Member> findById(Long id) {
        String sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE_NAME);
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);

        Member member = jdbcTemplate.queryForObject(sql, param, rowMapper);
        return Optional.ofNullable(member);
    }
    public Member save(Member member) {
        if (member.getId() == null) {
            return insert(member);
        }
        return update(member);
    }

    private Member insert(Member member) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName("Member")
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        Number id = simpleJdbcInsert.executeAndReturnKey(params);
        return Member.builder()
                     .id(id.longValue())
                     .email(member.getEmail())
                     .birthdate(member.getBirthdate())
                     .nickname(member.getNickname())
                     .createdAt(member.getCreatedAt())
                     .build();

    }

    private Member update(Member member) {
        String sql = String.format("UPDATE %s set email = :email, nickname = :nickname, birthdate = :birthdate WHERE id = :id", TABLE_NAME);
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        jdbcTemplate.update(sql, params);
        return member;
    }

    public List<Member> findAllByIdIn(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }

        String sql = String.format("SELECT * FROM %s WHERE id in (:ids)", TABLE_NAME);
        var params = new MapSqlParameterSource().addValue("ids", ids);
        return jdbcTemplate.query(sql, params, rowMapper);
    }
}
