package com.marriott.webapp.api;

import com.marriott.webapp.model.Reservation;
import com.marriott.webapp.service.ReservationRequest;
import com.marriott.webapp.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody final ReservationRequest reservationRequest) {
        final Reservation reservation = reservationService.createReservation(
                reservationRequest.getRoomId(),
                reservationRequest.getGuest(),
                reservationRequest.getStartDate(),
                reservationRequest.getEndDate(),
                reservationRequest.getCreditCardNumber());
        return ResponseEntity.ok(reservation);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservationsForGuest(@RequestParam final Long guestId) {
        final List<Reservation> reservations = reservationService.getReservationsForGuest(guestId);
        return ResponseEntity.ok(reservations);
    }

    @PutMapping("/{reservationId}/cancel")
    public ResponseEntity<Reservation> cancelReservation(@PathVariable final Long reservationId) {
        final Reservation reservation = reservationService.cancelReservation(reservationId);
        return ResponseEntity.ok(reservation);
    }
}