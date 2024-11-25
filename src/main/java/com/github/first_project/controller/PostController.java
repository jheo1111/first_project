package com.github.first_project.controller;

import com.github.first_project.dto.PostRequest;
import com.github.first_project.dto.PostResponse;
import com.github.first_project.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public String createPost(@RequestBody PostRequest postRequest) {
        postService.writePost(postRequest.getTitle(), postRequest.getContent(), postRequest.getAuthor());
        return "게시물이 성공적으로 작성되었습니다.";
    }

    @GetMapping
    public List<PostResponse> getPosts() {
        return postService.getAllPosts().stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }
}
