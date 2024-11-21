package com.github.first_project.config;

import com.github.first_project.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;

import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private final String secretKey = "project1"; // 원하는 비밀 키 설정

    @Autowired
    private CustomUserDetailsService customUserDetailsService; // 의존성 주입

    // UserDetails 가져오기
    public UserDetails getUserDetails(String username) {
        return customUserDetailsService.loadUserByUsername(username); // UserDetailsService를 통해 UserDetails 객체 가져오기
    }

    // JWT 생성
    public String createToken(String username, Map<String, Object> claims) {
        // 1시간 유효한 토큰 설정
        long validityInMilliseconds = 3600000;
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 서명에 사용할 알고리즘
                .compact();
    }

    // JWT에서 Claims 정보 추출
    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalStateException("Invalid JWT token", e);
        }
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token, UserDetails userDetails) {
        // 토큰에서 username 추출
        String username = extractClaims(token).getSubject();

        // 유효성 검사: 토큰의 username이 userDetails와 일치하고, 만료되지 않았는지 확인
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // JWT 만료 여부 확인
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // 인증 정보 반환
    public Authentication getAuthentication(String token) {
        // JWT에서 username 추출
        String username = extractClaims(token).getSubject();

        // UserDetailsService를 통해 UserDetails 객체 가져오기
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username); // 의존성 주입을 통해 가져오기

        // UsernamePasswordAuthenticationToken 객체 생성 및 반환
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null, // 비밀번호는 필요하지 않음
                userDetails.getAuthorities() // 사용자 권한 목록
        );
    }
}
