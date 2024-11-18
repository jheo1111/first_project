package com.github.first_project.service;

import com.github.first_project.domain.Member;
import com.github.first_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public Member signUp(String username, String password, String email) {
        Member member = Member.builder()
                .username(username)
                .password(password) // 실제 구현 시 암호화 필요
                .email(email)
                .build();
        return memberRepository.save(member);
    }
}
