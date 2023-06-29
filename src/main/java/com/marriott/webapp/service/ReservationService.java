package com.marriott.webapp.service;

import com.marriott.webapp.model.Guest;
import com.marriott.webapp.model.Reservation;
import com.marriott.webapp.model.ReservationRepository;
import com.marriott.webapp.model.Room;
import com.marriott.webapp.model.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.marriott.webapp.model.Reservation.*;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public Reservation createReservation(final Long roomId,
                                         final Guest guest,
                                         final LocalDate startDate,
                                         final LocalDate endDate,
                                         final String creditCardNumber) {
        // This is a basic implementation. You should add more logic here,
        // like checking if the room is available for the specified dates.
        final Room room = roomRepository.getReferenceById(roomId);
        final Reservation reservation = builder()
                                            .room(room)
                                            .guest(guest)
                                            .checkInDate(startDate)
                                            .checkOutDate(endDate)
                                            .status(ReservationStatus.ACTIVE)
                                            // You should probably encrypt the credit card number and not store it in plain text.
                                            .creditCardNumber(creditCardNumber)
                                            .build();

        return reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getReservationsForGuest(final Long guestId) {
        return reservationRepository.findAllByGuestId(guestId);
    }

    public Reservation cancelReservation(final Long reservationId) {
        final Reservation reservation = reservationRepository.getReferenceById(reservationId);
        final LocalDate now = LocalDate.now();

        if (now.isAfter(reservation.getCheckInDate().minusDays(1))) {
            throw new SystemConflictException("Reservation can only be cancelled within 24 hours from the check-in date.");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        return reservationRepository.save(reservation);
    }
}