package com.devcodes.projects.RoomStackReservationPlatform.service;

import com.devcodes.projects.RoomStackReservationPlatform.dto.HotelDto;
import com.devcodes.projects.RoomStackReservationPlatform.dto.HotelSearchRequest;
import com.devcodes.projects.RoomStackReservationPlatform.entity.RoomEntity;
import org.springframework.data.domain.Page;

public interface InventoryService {
    public void initializeRoomForAYear(RoomEntity room);

    void deleteAllInventories(RoomEntity room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
