package com.github.first_project.controller;

import com.github.first_project.dto.LikeRequest;
import com.github.first_project.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<String> likePost(@RequestBody LikeRequest request) {
        likeService.likePost(request);
        return ResponseEntity.ok("좋아요가 성공적으로 추가되었습니다.");
    }

    @DeleteMapping
    public ResponseEntity<String> unlikePost(@RequestBody LikeRequest request) {
        likeService.unlikePost(request);
        return ResponseEntity.ok("좋아요가 취소되었습니다.");
    }
}
