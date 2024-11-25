package com.github.first_project.service;

import com.github.first_project.domain.Post;
import com.github.first_project.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post writePost(String title, String content, String author) {
        return postRepository.save(new Post(title, content, author));
    }
}
