package com.marriott.webapp.service;

import com.marriott.webapp.model.CreditCard;
import com.marriott.webapp.model.Guest;
import com.marriott.webapp.model.MemberRepository;
import com.marriott.webapp.model.Reservation;
import com.marriott.webapp.model.ReservationRepository;
import com.marriott.webapp.model.Room;
import com.marriott.webapp.model.RoomRepository;
import com.marriott.webapp.model.StarwoodMember;
import com.marriott.webapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.marriott.webapp.model.Reservation.*;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final MemberRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

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
                                            .user(guest)
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
        return reservationRepository.findAllByUserId(guestId);
    }

    @Transactional
    public Reservation cancelReservation(final Long reservationId) {
        final Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation id"));

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new IllegalStateException("Cannot cancel a reservation that is not active");
        }

        final LocalDate now = LocalDate.now();

        if (now.isAfter(reservation.getCheckInDate().minusDays(1))) {
            throw new SystemConflictException("Reservation can only be cancelled within 24 hours from the check-in date.");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public List<Reservation> bookRooms(final List<Long> roomIds,
                                       final LocalDate startDate,
                                       final LocalDate endDate,
                                       final Long creditCardId) {

        final var member = (StarwoodMember) getAuthenticatedMember();
        final var creditCard = getCreditCard(member, creditCardId);

        List<Room> rooms = roomRepository.findAllById(roomIds);

        List<Reservation> reservations = createReservation(member, rooms, startDate, endDate, creditCard);

        return reservationRepository.saveAll(reservations);
    }

    private User getAuthenticatedMember() {
        final var authentication = authenticationFacade.getAuthentication();
        return userRepository.findById(Long.valueOf(authentication.getName())).orElseThrow();
    }

    private CreditCard getCreditCard(final StarwoodMember member, final Long creditCardId) {
        return member.getCreditCards().stream()
                .filter(creditCard -> creditCard.getId().equals(creditCardId))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid credit card id"));
    }

    private List<Reservation> createReservation(final StarwoodMember member,
                                                final List<Room> rooms,
                                                final LocalDate startDate,
                                                final LocalDate endDate,
                                                final CreditCard creditCard) {

        return rooms.stream()
                .map(room -> Reservation.builder()
                                        .room(room)
                                        .user(member)
                                        .checkInDate(startDate)
                                        .checkOutDate(endDate)
                                        .status(ReservationStatus.ACTIVE)
                                        // You should probably encrypt the credit card number and not store it in plain text.
                                        .creditCardNumber(creditCard.getCardNumber())
                                        .build())
                .collect(toList());
    }

}