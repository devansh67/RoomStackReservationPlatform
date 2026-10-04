package com.devcodes.projects.RoomStackReservationPlatform.repository;

import com.devcodes.projects.RoomStackReservationPlatform.dto.HotelPriceDto;
import com.devcodes.projects.RoomStackReservationPlatform.entity.HotelEntity;
import com.devcodes.projects.RoomStackReservationPlatform.entity.HotelMinPriceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface HotelMinPriceRepository extends JpaRepository<HotelMinPriceEntity, Long> {

    @Query("""
            SELECT new com.devcodes.projects.RoomStackReservationPlatform.dto.HotelPriceDto(i.hotel, AVG(i.price))
            FROM HotelMinPriceEntity i
            WHERE i.hotel.city = :city
                AND i.date BETWEEN :startDate AND :endDate
                AND i.hotel.isActive = true
            GROUP BY i.hotel
            """)
    Page<HotelPriceDto> findHotelsWithAvailableInventory(
            @Param("city") String city,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomsCount,
            @Param("dateCount") Long dateCount,
            Pageable pageable
    );

    Optional<HotelMinPriceEntity> findByHotelAndDate(HotelEntity hotelEntity, LocalDate date);
}
