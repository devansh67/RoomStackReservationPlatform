package com.devcodes.projects.RoomStackReservationPlatform.strategy;

import com.devcodes.projects.RoomStackReservationPlatform.entity.InventoryEntity;

import java.math.BigDecimal;

public class BasePricingStrategy implements PricingStrategy {
    @Override
    public BigDecimal calculatePrice(InventoryEntity inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
