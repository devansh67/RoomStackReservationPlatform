package com.devcodes.projects.RoomStackReservationPlatform.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomDto {
    Long id;
    String type;
    BigDecimal basePrice;
    String[] photos;
    String[] amenities;
    Integer totalCount;
    Integer capacity;
}
