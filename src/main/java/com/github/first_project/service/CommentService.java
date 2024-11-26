package com.github.first_project.service;

import com.github.first_project.dto.CommentRequest;
import com.github.first_project.domain.Comment;
import com.github.first_project.dto.EditCommentDTO;
import com.github.first_project.repository.CommentRepository;
import com.github.first_project.repository.PostRepository;
import com.github.first_project.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public void createComment(CommentRequest request) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setPost(postRepository.findById(request.getPostId()).orElseThrow(() -> new RuntimeException("게시물이 존재하지 않습니다.")));
        comment.setAuthor(memberRepository.findByEmail(request.getAuthor()).orElseThrow(() -> new RuntimeException("작성자가 존재하지 않습니다.")));
        commentRepository.save(comment);
    }

    public void updateComment(Long commentId, EditCommentDTO request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        comment.setContent(request.getContent());
        commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
