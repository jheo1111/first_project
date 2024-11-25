package com.github.first_project.repository;

import com.github.first_project.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByPostIdAndAuthorId(Long postId, Long authorId);
}
