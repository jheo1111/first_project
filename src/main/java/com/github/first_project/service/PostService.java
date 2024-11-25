package com.github.first_project.service;

import com.github.first_project.domain.Member;
import com.github.first_project.dto.PostDTO;
import com.github.first_project.dto.PostRequest;
import com.github.first_project.domain.Post;
import com.github.first_project.repository.PostRepository;
import com.github.first_project.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostDTO::new)  // Post 객체를 PostDTO로 변환
                .collect(Collectors.toList());
    }

    public List<PostDTO> getPostsByAuthor(String authorEmail) {
        List<Post> posts = postRepository.findByAuthorEmail(authorEmail);
        return posts.stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
    }


    public void createPost(PostRequest request) {
        Member author = memberRepository.findByEmail(request.getAuthor())
                .orElseThrow(() -> new IllegalArgumentException("작성자가 존재하지 않습니다."));

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(author) // 유효한 Member 객체 설정
                .build();

        postRepository.save(post);
    }

    public void updatePost(Long postId, PostRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
