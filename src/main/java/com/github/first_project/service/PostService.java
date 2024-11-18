package com.github.first_project.service;

import com.github.first_project.domain.Member;
import com.github.first_project.domain.Post;
import com.github.first_project.repository.MemberRepository;
import com.github.first_project.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Post writePost(String title, String body, Long writerMemberId) {
        Member writer = memberRepository.findById(writerMemberId)
                .orElseThrow(() -> new IllegalStateException("Member not found"));

        return postRepository.save(
                Post.builder()
                        .title(title)
                        .body(body)
                        .writer(writer)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    // 모든 게시물을 가져오는 메서드
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}

