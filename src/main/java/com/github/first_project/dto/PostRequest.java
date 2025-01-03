package com.github.first_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {

    @NotBlank(message = "게시물 제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "게시물 내용을 입력해주세요.")
    private String content;

    @NotBlank(message = "작성자 이메일을 입력해주세요.")
    private String author;
}
