package com.devcodes.projects.RoomStackReservationPlatform.strategy;

import com.devcodes.projects.RoomStackReservationPlatform.entity.InventoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy {

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(InventoryEntity inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);

        boolean isTodayHoliday = true; // call an API to figure out or check w local data source
        if(isTodayHoliday) {
            price = price.multiply(BigDecimal.valueOf(1.25));
        }

        return price;
    }
}
