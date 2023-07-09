package com.marriott.webapp.service;

import com.marriott.webapp.model.MemberRepository;
import com.marriott.webapp.model.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
class UserDetailsServiceImpl implements UserDetailsService, UserDetailsManager {

    private final MemberService memberService;

    @Override
    public void createUser(UserDetails user) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteUser(String username) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final var member = memberRepository.findByCredentialsUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(member);
    }


}
