package com.github.first_project.controller;

import com.github.first_project.dto.LoginRequest;
import com.github.first_project.dto.SignUpRequest;
import com.github.first_project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public String register(@RequestBody SignUpRequest signupRequest) {
        memberService.register(signupRequest.getEmail(), signupRequest.getPassword());
        return "회원가입이 완료되었습니다.";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        memberService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        return "로그인이 성공적으로 완료되었습니다.";
    }
}
