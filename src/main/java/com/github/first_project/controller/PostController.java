package com.github.first_project.controller;

import com.github.first_project.domain.Post;
import com.github.first_project.dto.AuthInfo;
import com.github.first_project.dto.PostRequest;
import com.github.first_project.dto.PostResponse;
import com.github.first_project.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> writePost(
            AuthInfo authInfo,
            @RequestBody PostRequest postRequest
    ) {
        Post post = postService.writePost(postRequest.getTitle(), postRequest.getBody(), authInfo.getMemberId());
        return ResponseEntity.ok(PostResponse.from(post));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(AuthInfo authInfo) {
        return ResponseEntity.ok(
                postService.getAllPosts().stream()
                        .map(PostResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
