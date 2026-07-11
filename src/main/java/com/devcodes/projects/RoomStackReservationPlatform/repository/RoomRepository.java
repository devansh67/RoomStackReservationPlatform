package com.devcodes.projects.RoomStackReservationPlatform.repository;

import com.devcodes.projects.RoomStackReservationPlatform.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
}
