package com.github.first_project.service;

import com.github.first_project.domain.Like;
import com.github.first_project.domain.Post;
import com.github.first_project.domain.Member;
import com.github.first_project.dto.LikeRequest;
import com.github.first_project.repository.LikeRepository;
import com.github.first_project.repository.PostRepository;
import com.github.first_project.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Arrays.stream;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public LikeService(LikeRepository likeRepository, PostRepository postRepository, MemberRepository memberRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void likePost(LikeRequest request) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));
        Member member = memberRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("해당 회원은 존재하지 않습니다."));

        Like like = new Like(post, member);
        likeRepository.save(like);
    }

    @Transactional
    public void unlikePost(LikeRequest request) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));
        Member member = memberRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("해당 회원은 존재하지 않습니다."));

        Like like = likeRepository.findByPostIdAndAuthorId(post.getId(), member.getId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("좋아요 정보가 없습니다."));
        likeRepository.delete(like);
    }
}
