package com.marriott.webapp.api;

import com.marriott.webapp.model.Room;
import com.marriott.webapp.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms(
        @RequestParam(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate startDate,
        @RequestParam(value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate endDate,
        @RequestParam(value = "capacity", required = false) final Integer capacity
    ) {
        if (capacity == null) return ResponseEntity.ok(roomService.findAvailableRooms(startDate, endDate));
        return ResponseEntity.ok(roomService.findAvailableRooms(startDate, endDate, capacity));
    }


}