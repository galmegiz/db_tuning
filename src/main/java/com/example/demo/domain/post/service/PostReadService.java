package com.example.demo.domain.post.service;

import com.example.demo.domain.post.dto.DailyPostCount;
import com.example.demo.domain.post.dto.DailyPostCountRequest;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.util.CursorRequest;
import com.example.demo.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostReadService {
    private final PostRepository postRepository;

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        return postRepository.groupByCreatedDate(request);
    }

    public Page<Post> getPosts(Long memberId, Pageable pageRequest) {
        return postRepository.findAllByMemberId(memberId, pageRequest);
    }

    public PageCursor<Post> getPosts(Long memberId, CursorRequest cursorRequest) {
        List<Post> posts = findAllBy(memberId, cursorRequest);
        long nextKey = posts.stream().mapToLong(Post::getId).min().orElse(CursorRequest.NONE_KEY);
        return new PageCursor(cursorRequest.next(nextKey), posts);

    }

    private List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        }else{
            return postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        }
    }
}
