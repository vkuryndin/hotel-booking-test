package org.example.service;

import org.example.dto.CreateBookingRequest;
import org.example.model.Booking;
import org.example.model.Room;
import org.example.repository.BookingRepository;
import org.example.repository.RoomRepository;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository,
                          RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking createBooking(CreateBookingRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (!request.getCheckOut().isAfter(request.getCheckIn())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Check-out date must be after check-in date"
            );
        }

        boolean roomBusy = bookingRepository
                .existsByRoomIdAndCheckInLessThanAndCheckOutGreaterThan(
                        room.getId(),
                        request.getCheckOut(),
                        request.getCheckIn()
                );

        if (roomBusy) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Room is already booked for these dates"
            );
        }
        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setGuestName(request.getGuestName());
        booking.setCheckIn(request.getCheckIn());
        booking.setCheckOut(request.getCheckOut());

        return bookingRepository.save(booking);
    }
    public List<Booking> getBookingsByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Booking not found"
            );
        }
        bookingRepository.deleteById(id);
    }
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Booking not found"
                ));
    }
}