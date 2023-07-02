package com.marriott.webapp.service;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservationsRequest {
    private List<Long> roomIds;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long creditCardId;
}