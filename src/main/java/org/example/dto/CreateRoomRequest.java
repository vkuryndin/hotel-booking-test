package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateRoomRequest {

    @NotNull
    private Long hotelId;

    @NotBlank
    private String roomNumber;

    @NotNull
    @Positive
    private Integer capacity;

    @NotNull
    @Positive
    private BigDecimal pricePerNight;
}