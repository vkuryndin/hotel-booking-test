package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateBookingRequest {

    @NotNull
    private Long roomId;

    @NotBlank
    private String guestName;

    @NotNull
    private LocalDate checkIn;

    @NotNull
    private LocalDate checkOut;
}