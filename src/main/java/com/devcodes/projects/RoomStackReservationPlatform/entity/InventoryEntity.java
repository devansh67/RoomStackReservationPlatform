package com.devcodes.projects.RoomStackReservationPlatform.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "inventory", uniqueConstraints = @UniqueConstraint(name = "unique_hotel_room_date", columnNames = {"hotel_id", "room_id", "date"}))
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id", nullable = false)
    HotelEntity hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    RoomEntity room;

    @Column(nullable = false)
    LocalDate date;

    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    Integer bookedCount;

    @Column(nullable = false)
    Integer totalCount;

    @Column(nullable = false, precision = 5, scale = 2)
    BigDecimal surgeFactor;

    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal price; // basePrice * surgeFactor

    @Column(nullable = false)
    String city;

    @Column(nullable = false)
    Boolean isClosed;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}
