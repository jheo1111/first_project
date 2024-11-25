package com.github.first_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {

    @NotNull(message = "게시물 ID를 입력해주세요.")
    private Long postId;

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private String content;

    @NotBlank(message = "작성자 이메일을 입력해주세요.")
    private String author;
}
