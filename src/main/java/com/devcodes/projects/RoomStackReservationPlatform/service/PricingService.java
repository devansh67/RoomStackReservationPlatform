package com.devcodes.projects.RoomStackReservationPlatform.service;

import com.devcodes.projects.RoomStackReservationPlatform.entity.InventoryEntity;
import com.devcodes.projects.RoomStackReservationPlatform.strategy.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricingService {

    public BigDecimal calculateDynamicPricing(InventoryEntity inventory) {
        PricingStrategy pricingStrategy = new BasePricingStrategy();

        pricingStrategy = new SurgePricingStrategy(pricingStrategy);
        pricingStrategy = new OccupancyPricingStrategy(pricingStrategy);
        pricingStrategy = new UrgencyPricingStrategy(pricingStrategy);
        pricingStrategy = new HolidayPricingStrategy(pricingStrategy);

        return pricingStrategy.calculatePrice(inventory);
    }
}
