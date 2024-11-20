package com.github.first_project.controller;

import com.github.first_project.dto.LoginRequest;
import com.github.first_project.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // UserDetailsService를 통해 사용자 정보를 조회합니다.

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        // 1. 사용자 정보 확인 (username, password 확인)
        // 2. 인증 성공 시 JWT 발급
        try {
            // 인증 로직 (이 예시는 간단한 예시로, 실제로는 AuthenticationManager와 인증 절차가 필요함)
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

            if (userDetails.getPassword().equals(loginRequest.getPassword())) {
                // 비밀번호가 맞다면 토큰 발급
                String token = jwtService.encode(Long.valueOf(userDetails.getUsername())); // encode 메서드에서 사용자의 ID나 username을 넣어 JWT를 발급
                return ResponseEntity.ok(Map.of("token", token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
        }
    }
}
