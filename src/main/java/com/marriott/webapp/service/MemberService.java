package com.marriott.webapp.service;

import com.marriott.webapp.model.MemberRepository;
import com.marriott.webapp.model.StarwoodMember;
import com.marriott.webapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

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

        final var user = memberRepository.findByContactEmail(member.getContact().getEmail());

        if (user.isPresent() && user.get().getType().equals(StarwoodMember.STARWOOD) ) {
            throw new SystemConflictException("Email already exists");
        } else if (user.isPresent()) {
            memberRepository.updateType(user.get().getId(), StarwoodMember.STARWOOD);
            user.ifPresent(value -> {
                member.setId(value.getId());
                member.setCreditCards(new ArrayList<>());
                member.setReservations(new ArrayList<>());
                member.setInactive(false);
            });
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
            .filter(user -> !user.getInactive())
            .orElseThrow(EntityNotFoundException::new);
    }
}