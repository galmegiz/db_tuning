package com.example.demo.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Member {
    private final Long id;
    private String nickname;
    private final String email;
    private final LocalDate birthdate;
    private final LocalDateTime createdAt;

    private final static Long NAME_MAX_LENGTH = 10l;
    @Builder
    public Member(Long id, String nickname, String email, LocalDate birthdate, LocalDateTime createdAt) {
        this.id = id;

        validateNickname(nickname);
        this.nickname = Objects.requireNonNull(nickname);
        this.email = Objects.requireNonNull(email);
        this.birthdate = Objects.requireNonNull(birthdate);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public void changeNickname(String other) {
        Objects.requireNonNull(other);
        validateNickname(other);
        nickname = other;
    }

    void validateNickname(String nickname) {
        Assert.isTrue(nickname.length() <= NAME_MAX_LENGTH, "길이를 초과했습니다");
    }
}
