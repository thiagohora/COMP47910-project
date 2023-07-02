package com.marriott.webapp.config;


import com.marriott.webapp.model.MemberRepository;
import com.marriott.webapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    private final MemberRepository memberRepository;

    @Override
    public UsernamePasswordAuthenticationToken convert(final Jwt jwt) {

        final User user = memberRepository.getReferenceById(Long.getLong(jwt.getSubject()));

        return new UsernamePasswordAuthenticationToken(user, jwt, Collections.emptyList());
    }
}
