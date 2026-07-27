package com.devcodes.projects.RoomStackReservationPlatform.service;

import com.devcodes.projects.RoomStackReservationPlatform.dto.HotelDto;
import com.devcodes.projects.RoomStackReservationPlatform.dto.HotelInfoDto;
import com.devcodes.projects.RoomStackReservationPlatform.entity.HotelEntity;
import org.jspecify.annotations.Nullable;

public interface HotelService {
    public HotelDto createNewHotel(HotelDto hotelDto);
    public HotelDto getHotelById(Long id);
    public HotelDto updateHotelById(Long id, HotelDto hotelDto);
    void deleteHotelById(Long id);
    void activateHotel(Long id);

    HotelInfoDto getHotelInfoById(Long hotelId);
}
