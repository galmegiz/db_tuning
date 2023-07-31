package com.example.demo.domain.follow.service;

import com.example.demo.domain.follow.entity.Follow;
import com.example.demo.domain.follow.repository.FollowRepository;
import com.example.demo.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Service
public class FollowWriteService {

    private final FollowRepository followRepository;
    public void create(MemberDto fromMember, MemberDto toMember) {
        Assert.isTrue(fromMember.id().equals(toMember.id()), "멤버가 동일합니다");

        Follow follow = Follow.builder()
                             .fromMemberId(fromMember.id())
                             .toMemberId(toMember.id())
                             .build();

        followRepository.save(follow);
    }
}
