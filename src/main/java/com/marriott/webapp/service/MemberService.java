package com.marriott.webapp.service;

import com.marriott.webapp.model.MemberRepository;
import com.marriott.webapp.model.StarwoodMember;
import com.marriott.webapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // You can use Spring's BCryptPasswordEncoder

    @Transactional
    public StarwoodMember register(final StarwoodMember member, final String username, final String password) {
        if (memberRepository.findByCredentialsUsername(username).isPresent()) {
            throw new SystemConflictException("Username already exists");
        }

        if (memberRepository.findByContactEmail(member.getContact().getEmail()).isPresent()) {
            throw new SystemConflictException("Email already exists");
        }

        if (password.length() < 8) {
            throw new SystemConflictException("Password must be at least 8 characters long");
        }

        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password));

        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public User getByUsername(final String username) {
        return memberRepository.findByCredentialsUsername(username)
            .orElseThrow(EntityNotFoundException::new);
    }
}