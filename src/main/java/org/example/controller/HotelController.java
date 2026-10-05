package org.example.controller;

import org.example.model.Hotel;
import org.example.service.HotelService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;

import java.util.List;

import org.example.model.Room;
import org.example.service.RoomService;
import java.util.List;

@RestController
@RequestMapping("/hotels")
@CrossOrigin(origins = "http://localhost:5173")
public class HotelController {

    private final HotelService hotelService;
    private final RoomService roomService;

    public HotelController(HotelService hotelService, RoomService roomService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
    }

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @PostMapping
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelService.createHotel(hotel);
    }

    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @GetMapping("/{id}/rooms")
    public List<Room> getRoomsByHotel(@PathVariable Long id) {
        return roomService.getRoomsByHotelId(id);
    }

    @GetMapping("/{id}/rooms/available")
    public List<Room> getAvailableRoomsByHotel(
            @PathVariable Long id,
            @RequestParam LocalDate checkIn,
            @RequestParam LocalDate checkOut
    ) {
        return roomService.getAvailableRoomsByHotelId(
                id,
                checkIn,
                checkOut
        );
    }
}