package com.devcodes.projects.RoomStackReservationPlatform.repository;

import com.devcodes.projects.RoomStackReservationPlatform.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
}
