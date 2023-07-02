package com.marriott.webapp.service;

import com.marriott.webapp.model.Room;
import com.marriott.webapp.model.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private static final double DISCOUNT = 0.10;  // 10% discount
    private final RoomRepository roomRepository;
    private final AuthenticationFacade authenticationFacade;


    @Transactional(readOnly = true)
    public List<Room> findAvailableRooms(final LocalDate startDate, final LocalDate endDate, final int capacity) {
        return roomRepository.findAvailableRooms(startDate, endDate, capacity);
    }

    @Transactional(readOnly = true)
    public List<Room> findAvailableRooms(final LocalDate startDate, final LocalDate endDate) {
        return roomRepository.findAvailableRooms(startDate, endDate);
    }

    private boolean isStarwoodMember() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_STARWOOD_MEMBER"));
    }

    private void applyDiscount(final Room room) {
        double discountedPrice = room.getDiscountPrice() * (1 - DISCOUNT);
        room.setDiscountPrice(discountedPrice);
    }
}