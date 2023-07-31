package com.example.demo.util;

import com.example.demo.domain.member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class MemberFixtureFactory {
    public static Member create(Long seed) {

        EasyRandomParameters easyRandomParameters = new EasyRandomParameters();
        easyRandomParameters.setSeed(seed);
        return new EasyRandom(easyRandomParameters).nextObject(Member.class);
    }

    public static Member create() {

        EasyRandomParameters easyRandomParameters = new EasyRandomParameters();

        return new EasyRandom(easyRandomParameters).nextObject(Member.class);
    }

}
