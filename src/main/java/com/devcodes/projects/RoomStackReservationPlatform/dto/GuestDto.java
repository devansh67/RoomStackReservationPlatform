package com.devcodes.projects.RoomStackReservationPlatform.dto;

import com.devcodes.projects.RoomStackReservationPlatform.entity.UserEntity;
import com.devcodes.projects.RoomStackReservationPlatform.entity.enums.Gender;
import jakarta.persistence.*;

public class GuestDto {
    Long id;
    UserEntity user;
    Gender gender;
    Integer age;
}
