package com.github.first_project.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @Builder
    public Comment(String content, LocalDateTime createdAt, Post post, Member writer) {
        this.content = content;
        this.createdAt = createdAt;
        this.post = post;
        this.writer = writer;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setWriter(Member writer) {
        this.writer = writer;
    }
}
