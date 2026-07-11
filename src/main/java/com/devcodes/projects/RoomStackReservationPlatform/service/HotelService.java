package com.devcodes.projects.RoomStackReservationPlatform.service;

import com.devcodes.projects.RoomStackReservationPlatform.dto.HotelDto;
import com.devcodes.projects.RoomStackReservationPlatform.entity.HotelEntity;

public interface HotelService {
    public HotelDto createNewHotel(HotelDto hotelDto);
    public HotelDto getHotelById(Long id);
    public HotelDto updateHotelById(Long id, HotelDto hotelDto);
    void deleteHotelById(Long id);
    void activateHotel(Long id);
}
