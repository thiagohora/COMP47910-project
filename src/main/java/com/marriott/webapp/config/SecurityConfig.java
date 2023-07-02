package com.marriott.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig {
/*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(final HttpSecurity http,
                                             final UserDetailsService userDetailsService,
                                             final PasswordEncoder passwordEncoder) throws Exception {

        final var authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .authenticationProvider(new AuthenticationProvider() {

                    @Override
                    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

                        final String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
                        if (ObjectUtils.isEmpty(username)) {
                            throw new BadCredentialsException("invalid login details");
                        }

                        try {
                            final UserDetails user = userDetailsService.loadUserByUsername(username);
                            return createSuccessfulAuthentication(authentication, user);
                        } catch (final UsernameNotFoundException exception) {
                            throw new BadCredentialsException("invalid login details");
                        }
                    }

                    private Authentication createSuccessfulAuthentication(final Authentication authentication,
                                                                          final UserDetails user) {
                        final var token = new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            authentication.getCredentials(),
                            user.getAuthorities()
                        );

                        token.setDetails(authentication.getDetails());
                        return token;
                    }

                    @Override
                    public boolean supports(final Class<?> authentication) {
                        return authentication.equals(UsernamePasswordAuthenticationToken.class);
                    }

                })
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }

 */

}

