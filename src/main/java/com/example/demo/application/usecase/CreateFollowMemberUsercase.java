package com.example.demo.application.usecase;

import com.example.demo.domain.follow.service.FollowWriteService;
import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateFollowMemberUsercase {

    private final MemberReadService memberReadService;
    private final FollowWriteService followWriteService;
    public void execute(Long fromMemberId, Long toMemberId) {
        MemberDto fromMember = memberReadService.getMember(fromMemberId);
        MemberDto toMember = memberReadService.getMember(fromMemberId);
        followWriteService.create(fromMember, toMember);
    }
}
