package org.example.service;

import org.example.dto.CreateRoomRequest;
import org.example.model.Hotel;
import org.example.model.Room;
import org.example.repository.HotelRepository;
import org.example.repository.RoomRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public RoomService(RoomRepository roomRepository,
                       HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }


    public Room createRoom(CreateRoomRequest request) {
        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Room room = new Room();
        room.setHotel(hotel);
        room.setRoomNumber(request.getRoomNumber());
        room.setCapacity(request.getCapacity());
        room.setPricePerNight(request.getPricePerNight());

        return roomRepository.save(room);
    }
    public List<Room> getRoomsByHotelId(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }
    public List<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        if (!checkOut.isAfter(checkIn)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Check-out date must be after check-in date"
            );
        }

        return roomRepository.findAvailableRooms(checkIn, checkOut);
    }

    public List<Room> getAvailableRoomsByHotelId(
            Long hotelId,
            LocalDate checkIn,
            LocalDate checkOut
    ) {
        if (!checkOut.isAfter(checkIn)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Check-out date must be after check-in date"
            );
        }

        return roomRepository.findAvailableRoomsByHotelId(
                hotelId,
                checkIn,
                checkOut
        );
    }
}