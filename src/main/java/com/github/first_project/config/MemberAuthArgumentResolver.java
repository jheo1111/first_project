package com.github.first_project.config;

import com.github.first_project.dto.AuthInfo;
import com.github.first_project.service.JwtService;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

public class MemberAuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;

    public MemberAuthArgumentResolver(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return AuthInfo.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        String authorization = webRequest.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new RuntimeException("Unauthorized access");
        }
        String token = authorization.substring(7);
        Map<String, Long> decodedToken = jwtService.decode(token);
        Long memberId = decodedToken.get(JwtService.CLAIM_NAME_MEMBER_ID);
        if (memberId == null) {
            throw new RuntimeException("Unauthorized access");
        }
        return AuthInfo.of(memberId);
    }

}
