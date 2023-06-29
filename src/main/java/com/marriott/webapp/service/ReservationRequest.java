package com.marriott.webapp.service;


import com.marriott.webapp.model.Guest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class ReservationRequest {
    private Long roomId;
    private Guest guest;
    private LocalDate startDate;
    private LocalDate endDate;
    private String creditCardNumber;

    // getters and setters
}