package com.devcodes.projects.RoomStackReservationPlatform.repository;

import com.devcodes.projects.RoomStackReservationPlatform.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
}
