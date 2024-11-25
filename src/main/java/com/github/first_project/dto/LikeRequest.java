package com.github.first_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeRequest {

    @NotNull(message = "게시물 ID를 입력해주세요.")
    private Long postId;

    @NotBlank(message = "회원 번호를 입력해주세요.")
    private Long authorId;
}
