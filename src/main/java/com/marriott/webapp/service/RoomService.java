package com.marriott.webapp.service;

import com.marriott.webapp.model.Room;
import com.marriott.webapp.model.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public List<Room> findAvailableRooms(final LocalDate startDate, final LocalDate endDate, final int capacity) {
        return roomRepository.findAvailableRooms(startDate, endDate, capacity);
    }

    @Transactional(readOnly = true)
    public List<Room> findAvailableRooms(final LocalDate startDate, final LocalDate endDate) {
        return roomRepository.findAvailableRooms(startDate, endDate);
    }
}