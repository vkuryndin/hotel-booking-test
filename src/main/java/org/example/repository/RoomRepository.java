package org.example.repository;

import org.example.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotelId(Long hotelId);

    @Query("""
        SELECT r
        FROM Room r
        WHERE NOT EXISTS (
            SELECT b
            FROM Booking b
            WHERE b.room = r
              AND b.checkIn < :checkOut
              AND b.checkOut > :checkIn
        )
        """)
    List<Room> findAvailableRooms(
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );

    @Query("""
        SELECT r
        FROM Room r
        WHERE r.hotel.id = :hotelId
          AND NOT EXISTS (
              SELECT b
              FROM Booking b
              WHERE b.room = r
                AND b.checkIn < :checkOut
                AND b.checkOut > :checkIn
          )
        """)
    List<Room> findAvailableRoomsByHotelId(
            @Param("hotelId") Long hotelId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );

}