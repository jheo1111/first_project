package com.github.first_project.config;

import lombok.Getter;

@Getter
public class JwtAuthenticationResponse {
    private String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

}

