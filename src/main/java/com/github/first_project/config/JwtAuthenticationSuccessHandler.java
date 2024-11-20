package com.github.first_project.config;

import com.github.first_project.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    public JwtAuthenticationSuccessHandler(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        try {
            String username = authentication.getName();
            String token = jwtService.encode(Long.parseLong(username)); // 사용자의 고유 ID를 기반으로 JWT 생성

            response.setContentType("application/json");
            response.getWriter().write("{\"token\": \"" + token + "\"}");
            response.getWriter().flush();
        } catch (Exception e) {
            // 예외 처리 추가
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Internal server error\"}");
            response.getWriter().flush();
        }
    }

}
