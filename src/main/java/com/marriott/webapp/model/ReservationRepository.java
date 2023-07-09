package com.marriott.webapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUserId(final Long guestId);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.user.name like %:name% "
            + "AND r.user.surname like %:surname% "
            + "AND r.user.contact.email = :email "
            + "AND r.status = 'ACTIVE'")
    List<Reservation> findByNameAndSurnameAndEmail(String name, String surname, String email);
}
