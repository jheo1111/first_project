package com.github.first_project.service;

import com.github.first_project.domain.Member;
import com.github.first_project.repository.MemberRepository;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Getter
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository; // Assume you have a UserRepository for DB operations

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = MemberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return (UserDetails) Member.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .email(member.getEmail()) // e.g., "USER", "ADMIN"
                .build();
    }

}
