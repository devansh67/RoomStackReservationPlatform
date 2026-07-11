package com.devcodes.projects.RoomStackReservationPlatform.entity;

import com.devcodes.projects.RoomStackReservationPlatform.entity.Helper.HotelContactInfo;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hotel")
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    String city;

    @Column(columnDefinition = "TEXT[]")
    String[] photos;

    @Column(columnDefinition = "TEXT[]")
    String[] amenities;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Embedded
    HotelContactInfo contactInfo; // contact_info_address etc

    @Column(nullable = false)
    Boolean isActive;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    List<RoomEntity> rooms;

    @ManyToOne
    private UserEntity owner;
}
