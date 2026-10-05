package org.example.repository;

import org.example.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByRoomIdAndCheckInLessThanAndCheckOutGreaterThan(
            Long roomId,
            LocalDate checkOut,
            LocalDate checkIn
    );
    List<Booking> findByRoomId(Long roomId);
}

