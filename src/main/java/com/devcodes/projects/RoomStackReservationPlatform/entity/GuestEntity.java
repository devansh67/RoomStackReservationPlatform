package com.devcodes.projects.RoomStackReservationPlatform.entity;

import com.devcodes.projects.RoomStackReservationPlatform.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "guest")
public class GuestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    UserEntity user;

    @Enumerated(EnumType.STRING)
    Gender gender;

    Integer age;
}
