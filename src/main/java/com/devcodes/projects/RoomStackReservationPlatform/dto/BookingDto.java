package com.devcodes.projects.RoomStackReservationPlatform.dto;

import com.devcodes.projects.RoomStackReservationPlatform.entity.enums.BookingStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    Long id;
    Long hotelId;
    Long roomId;
    Long userId;
    Integer roomsCount;
    LocalDate checkInDate;
    LocalDate checkOutDate;
    BigDecimal amount;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    BookingStatus bookingStatus;
    Set<GuestDto> guests;
}
