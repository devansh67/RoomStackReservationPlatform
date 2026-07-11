package com.devcodes.projects.RoomStackReservationPlatform.dto;

import com.devcodes.projects.RoomStackReservationPlatform.entity.Helper.HotelContactInfo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HotelDto {
    Long id;
    String name;
    String city;
    String[] photos;
    String[] amenities;
    HotelContactInfo contactInfo; // contact_info_address etc
    Boolean isActive;
}
