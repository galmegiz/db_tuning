package com.example.demo.domain.member.service;

import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.member.dto.MemberNicknameHistoryDto;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.entity.MemberNameHistory;
import com.example.demo.domain.member.reporsitory.MemberHistoryRepository;
import com.example.demo.domain.member.reporsitory.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberReadService {
    private final MemberRepository memberRepository;
    private final MemberHistoryRepository memberHistoryRepository;

    public MemberDto getMember(Long id) {
        return toDto(memberRepository.findById(id).orElseThrow());
    }

    public List<MemberNicknameHistoryDto> getNicknameHistories(Long memberId) {
       return memberHistoryRepository.findAllByMemberId(memberId).stream().map(this::toDto).toList();
    }

    public List<MemberDto> getMembers(List<Long> ids) {
        List<Member> members = memberRepository.findAllByIdIn(ids);
        return members.stream().map(this::toDto).toList();
    }

    public MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getBirthdate());
    }

    private MemberNicknameHistoryDto toDto(MemberNameHistory history) {
        return new MemberNicknameHistoryDto(history.getId(), history.getMemberId(), history.getNickname());
    }
}
