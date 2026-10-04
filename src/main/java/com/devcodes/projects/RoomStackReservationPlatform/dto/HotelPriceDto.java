package com.devcodes.projects.RoomStackReservationPlatform.dto;

import lombok.Data;

@Data
public class HotelPriceDto {
    private HotelDto hotel;
    private Double price;
}
