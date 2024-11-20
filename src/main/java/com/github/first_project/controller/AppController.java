package com.github.first_project.controller;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    // CSRF 토큰을 HttpServletRequest에서 가져와서 모델에 추가
    @GetMapping("/login")
    public String home(Model model, HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("csrf");
        model.addAttribute("csrfToken", csrfToken != null ? csrfToken.getToken() : null);
        return "index"; // React 앱을 렌더링하는 HTML 파일
    }
}

