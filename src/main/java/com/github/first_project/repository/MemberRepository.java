package com.github.first_project.repository;

import com.github.first_project.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // Spring Data JPA가 자동으로 구현해주는 쿼리 메서드
    static Optional<Member> findByUsername(String username) {
        return Optional.empty();
    }
}
