package com.devcodes.projects.RoomStackReservationPlatform.entity.Helper;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class HotelContactInfo {
    String address;
    String phoneNumber;
    String email;
    String location;
}
