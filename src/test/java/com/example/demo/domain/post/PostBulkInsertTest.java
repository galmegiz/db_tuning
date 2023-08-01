package com.example.demo.domain.post;

import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.util.PostFixtureTest;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class PostBulkInsertTest {

    @Autowired
    PostRepository postRepository;

    @Test
    public void bulkInsert() {
        EasyRandom easyRandom = PostFixtureTest.get(3L, LocalDate.of(1970, 1, 1), LocalDate.of(2022, 2, 1));
        /*IntStream.range(0, 10)
                 .mapToObj(i -> easyRandom.nextObject(Post.class))
                 .forEach(
                         obj -> System.out.println(obj.getMemberId() + "/" + obj.getCreatedDate())
                 );*/

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<Post> posts = IntStream.range(0, 1000)
                                    .parallel()
                                    .mapToObj(i -> easyRandom.nextObject(Post.class))
                                    .toList();
        stopWatch.stop();
        System.out.println("stopWatch = " + stopWatch.getTotalTimeSeconds());
        StopWatch queryStopwatch = new StopWatch();
        queryStopwatch.start();
        postRepository.bulkInsert(posts);
        queryStopwatch.stop();
        System.out.println("stopWatch = " + queryStopwatch.getTotalTimeSeconds());

    }
}
