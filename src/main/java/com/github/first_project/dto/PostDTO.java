package com.github.first_project.dto;

import com.github.first_project.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String authorEmail;
    private LocalDateTime createdAt;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.authorEmail = post.getAuthor().getEmail();  // Member 객체에서 email만 사용
        this.createdAt = post.getCreatedAt();
    }
}
