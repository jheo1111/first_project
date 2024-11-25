package com.github.first_project.config;

import com.github.first_project.domain.Member;
import com.github.first_project.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.method.support.ModelAndViewContainer;

public class MemberAuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;

    public MemberAuthArgumentResolver(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = getTokenFromRequest(request);

        if (token == null || !jwtService.validateToken(token)) {
            throw new RuntimeException("Invalid or missing token");
        }

        Long memberId = jwtService.decode(token).get(JwtService.CLAIM_NAME_MEMBER_ID);
        return new Member(memberId);  // 반환하는 객체를 실제로 어떻게 구성할지에 따라 수정 가능
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}