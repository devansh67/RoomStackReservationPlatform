package com.devcodes.projects.RoomStackReservationPlatform.strategy;

import com.devcodes.projects.RoomStackReservationPlatform.entity.InventoryEntity;

import java.math.BigDecimal;

public interface PricingStrategy {

    BigDecimal calculatePrice(InventoryEntity inventory);
}
