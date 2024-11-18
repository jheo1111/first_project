package com.github.first_project.dto;

import com.github.first_project.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class PostResponse {
    private Long postId;
    private Long writerMemberId;
    private String title;
    private String body;
    private LocalDateTime createdAt;

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .writerMemberId(post.getWriter().getId())
                .title(post.getTitle())
                .body(post.getBody())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
