package com.github.first_project.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        try {
            String username = authentication.getName();
            String token = jwtTokenProvider.createToken(username); // Issue token based on the username

            response.setContentType("application/json");
            response.getWriter().write("{\"token\": \"" + token + "\"}");
            response.getWriter().flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Internal server error\"}");
            response.getWriter().flush();
        }
    }
}
