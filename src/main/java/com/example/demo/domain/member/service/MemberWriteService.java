package com.example.demo.domain.member.service;

import com.example.demo.domain.member.dto.RegisterMemberCommand;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.entity.MemberNameHistory;
import com.example.demo.domain.member.reporsitory.MemberHistoryRepository;
import com.example.demo.domain.member.reporsitory.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {
    private final MemberRepository memberRepository;
    private final MemberHistoryRepository memberHistoryRepository;

    public Member register(RegisterMemberCommand command) {
        Member member = Member.builder()
                              .nickname(command.nickname())
                              .email(command.email())
                              .birthdate(command.birthdate())
                              .build();
        Member savedMember = memberRepository.save(member);
        saveMemberHistory(savedMember);
        return savedMember;
    }

    public Member changeNickname(Long memberId, String nickname) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);

        saveMemberHistory(member);

        return memberRepository.save(member);
    }

    private void saveMemberHistory(Member member) {
        MemberNameHistory history = MemberNameHistory.builder()
                                                     .memberId(member.getId())
                                                     .nickname(member.getNickname())
                                                     .build();
        memberHistoryRepository.save(history);

    }
}
