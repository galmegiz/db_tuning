package com.example.demo.util;

import com.example.demo.domain.post.entity.Post;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.function.Predicate;

import static org.jeasy.random.FieldPredicates.*;

public class PostFixtureTest {

    static public EasyRandom get(Long memberId, LocalDate firstDate, LocalDate endDate) {
        Predicate<Field> idField = named("idd").and(ofType(Long.class))
                                                          .and(inClass(Post.class));
        Predicate<Field> memberIdField = named("memberId").and(ofType(Long.class))
                                                          .and(inClass(Post.class));
        EasyRandomParameters param = new EasyRandomParameters()
                .excludeField(idField)
                .dateRange(firstDate, endDate)
                .randomize(memberIdField, () -> memberId);
        return new EasyRandom(param);
    }
}
