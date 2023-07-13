package com.marriott.webapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<User, Long> {
    Optional<User> findByCredentialsUsername(final String username);

    Optional<User> findByContactEmail(final String email);

    @Query(value = "UPDATE user u SET u.type = ?2 WHERE u.id = ?1", nativeQuery = true)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void updateType(Long id, String starwood);
}
