package com.github.first_project.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public static final String CLAIM_NAME_MEMBER_ID = "memberId";

    // SecretKey 생성 메서드
    public SecretKey getSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256); // or HS512, depending on your algorithm
    }

    public String generateToken(Long memberId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_NAME_MEMBER_ID, memberId);

        SecretKey secretKey = getSecretKey(); // Get the secure key
        return Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .signWith(secretKey)
                .compact();
    }

    public Map<String, Long> decode(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())  // 안전한 키 사용
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Long memberId = claims.get(CLAIM_NAME_MEMBER_ID, Long.class);
            Map<String, Long> decodedToken = new HashMap<>();
            decodedToken.put(CLAIM_NAME_MEMBER_ID, memberId);

            return decodedToken;
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expired", e);
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid token", e);
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())  // 안전한 키 사용
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
