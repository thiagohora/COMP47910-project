package com.marriott.webapp.config;


import com.marriott.webapp.model.MemberRepository;
import com.marriott.webapp.model.User;
import com.marriott.webapp.service.UserDetailsImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UsernamePasswordAuthenticationToken convert(final Jwt jwt) {
        try {
            final User member = memberRepository.getReferenceById(Long.valueOf(jwt.getSubject()));
            return new UsernamePasswordAuthenticationToken(UserDetailsImpl.build(member), jwt, Collections.emptyList());
        } catch (final EntityNotFoundException e) {
            throw new UsernameNotFoundException("User Not Found with username: " + jwt.getSubject());
        }
    }
}
