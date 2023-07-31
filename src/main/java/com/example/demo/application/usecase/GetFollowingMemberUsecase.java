package com.example.demo.application.usecase;

import com.example.demo.domain.follow.entity.Follow;
import com.example.demo.domain.follow.service.FollowReadService;
import com.example.demo.domain.member.dto.MemberDto;
import com.example.demo.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetFollowingMemberUsecase {

    private final FollowReadService followReadService;
    private final MemberReadService memberReadService;
    public List<MemberDto> execute(Long memberId) {
        List<Follow> followings = followReadService.getFollowings(memberId);
        List<Long> memberIds = followings.stream().map(Follow::getToMemberId).toList();
        return memberReadService.getMembers(memberIds);
    }
}
