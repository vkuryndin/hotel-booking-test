package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateBookingRequest {

    private Long roomId;
    private String guestName;
    private LocalDate checkIn;
    private LocalDate checkOut;
}