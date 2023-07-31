package com.example.demo.application.controller;

import com.example.demo.application.usecase.CreateFollowMemberUsercase;
import com.example.demo.application.usecase.GetFollowingMemberUsecase;
import com.example.demo.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {
    private final CreateFollowMemberUsercase createUsecase;
    private final GetFollowingMemberUsecase getUsecase;
    @PostMapping("/{fromId}/{toId}")
    public void follow(@PathVariable Long fromId, @PathVariable Long toId) {
        createUsecase.execute(fromId, toId);
    }

    @GetMapping("/members/{fromId}")
    public List<MemberDto> follow(@PathVariable Long fromId) {
        return getUsecase.execute(fromId);

    }
}
