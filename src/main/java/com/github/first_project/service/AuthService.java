package com.github.first_project.service;

import com.github.first_project.dto.LoginRequest;
import com.github.first_project.dto.SignupRequest;
import com.github.first_project.domain.Member;
import com.github.first_project.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void signup(SignupRequest request) {
        String email = request.getEmail();
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        memberRepository.save(member);
    }

    public String login(LoginRequest request) {
        Optional<Member> member = memberRepository.findByEmail(request.getEmail());

        if (member.isEmpty() || !passwordEncoder.matches(request.getPassword(), member.get().getPassword())) {
            throw new RuntimeException("잘못된 이메일 또는 비밀번호입니다.");
        }

        return jwtService.generateToken(member.get().getId());
    }
}
