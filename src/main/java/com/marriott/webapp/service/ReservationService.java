package com.marriott.webapp.service;

import com.marriott.webapp.model.CreditCard;
import com.marriott.webapp.model.Guest;
import com.marriott.webapp.model.MemberRepository;
import com.marriott.webapp.model.Reservation;
import com.marriott.webapp.model.ReservationRepository;
import com.marriott.webapp.model.Room;
import com.marriott.webapp.model.RoomRepository;
import com.marriott.webapp.model.StarwoodMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.marriott.webapp.model.Reservation.ReservationStatus;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final MemberRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

    @Transactional
    public Reservation createReservation(final List<Long> roomIds,
                                         final Guest guest,
                                         final LocalDate startDate,
                                         final LocalDate endDate,
                                         final String creditCardNumber) {

        if (startDate.isAfter(endDate)) {
            throw new ClientErrorException("Invalid date range");
        }

        if (roomIds.isEmpty()) {
            throw new ClientErrorException("At least one room must be selected");
        }

        final List<Room> rooms = roomRepository.findAllById(roomIds);

        final var reservationGuest = userRepository.findByContactEmail(guest.getContact().getEmail())
                .orElseGet(() -> userRepository.save(guest));

        // This is a basic implementation. You should add more logic here,
        // like checking if the room is available for the specified dates.
        final Reservation reservation = Reservation.builder()
                                            .rooms(Set.copyOf(rooms))
                                            .user(reservationGuest)
                                            .checkInDate(startDate)
                                            .checkOutDate(endDate)
                                            .status(ReservationStatus.ACTIVE)
                                            // You should probably encrypt the credit card number and not store it in plain text.
                                            .creditCardNumber(creditCardNumber)
                                            .build();

        return reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getReservationsForUser() {
        return reservationRepository.findAllByUserId(getAuthenticatedMember().getId());
    }

    @Transactional
    public void cancelReservation(final Long reservationId) {
        final Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation id"));

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new SystemConflictException("Cannot cancel a reservation that is not active");
        }

        final LocalDate now = LocalDate.now();

        if (now.isAfter(reservation.getCheckInDate().minusDays(1))) {
            throw new SystemConflictException("Reservation can only be cancelled within 24 hours from the check-in date.");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    @Transactional
    public List<Reservation> bookRooms(final List<Long> roomIds,
                                       final LocalDate startDate,
                                       final LocalDate endDate,
                                       final Long creditCardId) {

        final var member = getAuthenticatedMember();
        final var creditCard = getCreditCard(member, creditCardId);

        final List<Room> rooms = roomRepository.findAllById(roomIds);

        final var reservation = createReservation(member, rooms, startDate, endDate, creditCard);

        return List.of(reservationRepository.save(reservation));
    }

    private StarwoodMember getAuthenticatedMember() {
        return authenticationFacade.getStarwoodMember().orElseThrow();
    }

    private CreditCard getCreditCard(final StarwoodMember member, final Long creditCardId) {
        return member.getCreditCards().stream()
                .filter(creditCard -> creditCard.getId().equals(creditCardId))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid credit card id"));
    }

    private Reservation createReservation(final StarwoodMember member,
                                                final List<Room> rooms,
                                                final LocalDate startDate,
                                                final LocalDate endDate,
                                                final CreditCard creditCard) {

        return Reservation.builder()
                    .rooms(Set.copyOf(rooms))
                    .user(member)
                    .checkInDate(startDate)
                    .checkOutDate(endDate)
                    .status(ReservationStatus.ACTIVE)
                    // You should probably encrypt the credit card number and not store it in plain text.
                    .creditCardNumber(creditCard.getCardNumber())
                    .build();
    }

    @Transactional(readOnly = true)
    public List<Reservation> findByNameAndSurnameAndEmail(final String name, final String surname, final String email) {
        return reservationRepository.findByNameAndSurnameAndEmail(name, surname, email);
    }

    @Transactional(readOnly = true)
    public Reservation getReservation(final Long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(EntityNotFoundException::new);
    }
}