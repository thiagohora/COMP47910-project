package com.marriott.webapp.service;


import com.marriott.webapp.model.Guest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class ReservationRequest {

    private List<Long> roomIds;
    private Guest guest;
    private LocalDate startDate;
    private LocalDate endDate;
    private String creditCard;

    // getters and setters
}