package com.devcodes.projects.RoomStackReservationPlatform.service;

import com.devcodes.projects.RoomStackReservationPlatform.entity.HotelEntity;
import com.devcodes.projects.RoomStackReservationPlatform.entity.HotelMinPriceEntity;
import com.devcodes.projects.RoomStackReservationPlatform.entity.InventoryEntity;
import com.devcodes.projects.RoomStackReservationPlatform.repository.HotelMinPriceRepository;
import com.devcodes.projects.RoomStackReservationPlatform.repository.HotelRepository;
import com.devcodes.projects.RoomStackReservationPlatform.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PricingUpdateService {

    // Schedular to update the inventory and HotelMinPrice tables every hour

    private final HotelRepository hotelRepository;
    private final InventoryRepository inventoryRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final PricingService pricingService;

    @Scheduled(cron = "*/5 * * * * *")
    public void updatePrices() {
        int page = 0;
        int batchSize = 100;

        while(true) {
            Page<HotelEntity> hotelEntityPage = hotelRepository.findAll(PageRequest.of(page, batchSize));
            if (hotelEntityPage.isEmpty()) {
                break;
            }
            hotelEntityPage.getContent().forEach(this::updateHotelPrices);

            page++;
        }
    }

    private void updateHotelPrices(HotelEntity hotelEntity) {
        log.info("Updating hotel prices for hotel ID: {}", hotelEntity.getId());
        LocalDate startDate = LocalDate.now();
        LocalDate endDate  = LocalDate.now().plusYears(1);

        List<InventoryEntity> inventoryEntityList = inventoryRepository.findByHotelAndDateBetween(hotelEntity, startDate, endDate);

        updateInventoryPrices(inventoryEntityList);

        updateHotelMinPrice(hotelEntity, inventoryEntityList, startDate, endDate);
    }

    private void updateHotelMinPrice(HotelEntity hotelEntity, List<InventoryEntity> inventoryEntityList, LocalDate startDate, LocalDate endDate) {

        // Compute minimum price per day for the hotel
        Map<LocalDate, BigDecimal> dailyMinPrices = inventoryEntityList.stream()
                .collect(Collectors.groupingBy(
                        InventoryEntity::getDate,
                        Collectors.mapping(InventoryEntity::getPrice, Collectors.minBy(Comparator.naturalOrder()))
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e-> e.getValue().orElse(BigDecimal.ZERO)));

        // Prepare HotelPrice entities in bulk
        List< HotelMinPriceEntity> hotelPrices = new ArrayList<>();
        dailyMinPrices.forEach((date, price) -> {
             HotelMinPriceEntity hotelMinPriceEntity = hotelMinPriceRepository.findByHotelAndDate(hotelEntity, date)
                     .orElse(new HotelMinPriceEntity(hotelEntity, date));
             hotelMinPriceEntity.setPrice(price);
             hotelPrices.add(hotelMinPriceEntity);
        });

        // Save all HotelPrice entities in bulk
        hotelMinPriceRepository.saveAll(hotelPrices);
    }

    private void updateInventoryPrices(List<InventoryEntity> inventoryEntityList) {
        inventoryEntityList.forEach(inventory -> {
            BigDecimal dynamicPrice = pricingService.calculateDynamicPricing(inventory);
            inventory.setPrice(dynamicPrice);
        });

        inventoryRepository.saveAll(inventoryEntityList);
    }
}
