package com.example.demo.domain.member.entity;

import com.example.demo.util.MemberFixtureFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {


    @DisplayName("회원의 닉네임을 변경할 수 있다.")
    @Test
    public void testChangeName() {
        //ObjectMother pattern
        Member member = MemberFixtureFactory.create();
        String other = "sun";

        member.changeNickname(other);
        Assertions.assertThat(member.getNickname()).isEqualTo(other);
    }

    @DisplayName("회원의 닉네임 초과")
    @Test
    public void testNickNameMaxLength() {
        //ObjectMother pattern
        Member member = MemberFixtureFactory.create();
        String overNicknameLength = "suasdfasdfasdfasdfn";

        Assertions.assertThatThrownBy(() -> member.changeNickname(overNicknameLength)).isInstanceOf(IllegalArgumentException.class);

    }
}