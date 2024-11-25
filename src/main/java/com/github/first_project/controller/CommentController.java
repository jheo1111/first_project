package com.github.first_project.controller;

import com.github.first_project.dto.CommentRequest;
import com.github.first_project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<?> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CommentRequest request) {
        commentService.createComment(request);
        return ResponseEntity.ok("댓글이 성공적으로 작성되었습니다.");
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long commentId, @RequestBody CommentRequest request) {
        commentService.updateComment(commentId, request);
        return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }
}
