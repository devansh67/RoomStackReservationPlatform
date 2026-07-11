package com.devcodes.projects.RoomStackReservationPlatform.repository;

import com.devcodes.projects.RoomStackReservationPlatform.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
}