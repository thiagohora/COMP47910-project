package com.marriott.webapp.service;

import com.marriott.webapp.model.StarwoodMember;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface AuthenticationFacade {
    Authentication getAuthentication();

    Optional<StarwoodMember> getStarwoodMember();
}
