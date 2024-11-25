package com.github.first_project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String body;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private Member writer;
}
