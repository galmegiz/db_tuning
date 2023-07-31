package com.example.demo.domain.member.reporsitory;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.entity.MemberNameHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberHistoryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final String TABLE_NAME = "MemberNicknameHistory";

    public static RowMapper<MemberNameHistory> rowMapper = (ResultSet resultSet, int rowNum) -> MemberNameHistory.builder()
                                                                                                                 .memberId(resultSet.getLong("memberId"))
                                                                                                                 .id(resultSet.getLong("id"))
                                                                                                                 .nickname(resultSet.getString("nickname"))
                                                                                                                 .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
                                                                                                                 .build();

    public MemberNameHistory save(MemberNameHistory history) {
        if (history.getId() == null) {
            return insert(history);
        }
        throw new UnsupportedOperationException("history는 갱신 불가");
    }

    private MemberNameHistory insert(MemberNameHistory history) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(history);
        Number id = simpleJdbcInsert.executeAndReturnKey(params);
        return MemberNameHistory.builder()
                                .id(id.longValue())
                                .memberId(history.getMemberId())
                                .nickname(history.getNickname())
                                .createdAt(history.getCreatedAt())
                                .build();

    }

    public List<MemberNameHistory> findAllByMemberId(Long memberId) {
        String sql = String.format("SELECT * FROM %s WHERE memberId = :memberId", TABLE_NAME);
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("memberId", memberId);
        return jdbcTemplate.query(sql, param, rowMapper);

    }

}
