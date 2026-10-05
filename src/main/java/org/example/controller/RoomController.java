package org.example.controller;

import org.example.dto.CreateRoomRequest;
import org.example.model.Room;
import org.example.model.Booking;
import org.example.service.BookingService;
import org.example.service.RoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;


import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;
    private final BookingService bookingService;

    public RoomController(RoomService roomService,
                          BookingService bookingService) {
        this.roomService = roomService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping
    public Room createRoom(@RequestBody CreateRoomRequest request) {
        return roomService.createRoom(request);
    }

    @GetMapping("/{id}/bookings")
    public List<Booking> getBookingsByRoom(@PathVariable Long id) {
        return bookingService.getBookingsByRoomId(id);
    }

    @GetMapping("/available")
    public List<Room> getAvailableRooms(
            @RequestParam LocalDate checkIn,
            @RequestParam LocalDate checkOut
    ) {
        return roomService.getAvailableRooms(checkIn, checkOut);
    }
}