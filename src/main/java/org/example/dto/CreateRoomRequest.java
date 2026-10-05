package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateRoomRequest {

    private Long hotelId;
    private String roomNumber;
    private Integer capacity;
    private BigDecimal pricePerNight;
}