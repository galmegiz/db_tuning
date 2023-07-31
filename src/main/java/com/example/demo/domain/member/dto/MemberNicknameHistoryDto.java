package com.example.demo.domain.member.dto;

public record MemberNicknameHistoryDto(
        Long id,
        Long memberId,
        String nickname
) {
}
