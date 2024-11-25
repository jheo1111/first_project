package com.github.first_project.controller;

import com.github.first_project.domain.Member;
import com.github.first_project.domain.Post;
import com.github.first_project.dto.PostRequest;
import com.github.first_project.repository.MemberRepository;
import com.github.first_project.repository.PostRepository;
import com.github.first_project.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Transactional
public class PostController {

    private final PostService postService;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/search")
    public ResponseEntity<?> getPostsByAuthor(@RequestParam("author_email") String authorEmail) {
        return ResponseEntity.ok(postService.getPostsByAuthor(authorEmail));
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
        Member member = memberRepository.findByEmail(postRequest.getAuthor())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Post post = new Post();
        post.setAuthor(member);
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        postRepository.save(post);

        return ResponseEntity.ok("게시글을 성공적으로 작성하였습니다.");
    }

    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable Long postId, @RequestBody PostRequest request) {
        // 게시물을 조회하여
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        // title과 content만 수정
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        // 업데이트된 게시물 저장
        postRepository.save(post);

        return ResponseEntity.ok("게시물이 성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("게시물이 삭제되었습니다.");
    }
}
