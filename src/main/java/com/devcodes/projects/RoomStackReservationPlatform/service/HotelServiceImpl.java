package com.devcodes.projects.RoomStackReservationPlatform.service;

import com.devcodes.projects.RoomStackReservationPlatform.dto.HotelDto;
import com.devcodes.projects.RoomStackReservationPlatform.entity.HotelEntity;
import com.devcodes.projects.RoomStackReservationPlatform.entity.RoomEntity;
import com.devcodes.projects.RoomStackReservationPlatform.exceptions.ResourceNotFoundException;
import com.devcodes.projects.RoomStackReservationPlatform.repository.HotelRepository;
import com.devcodes.projects.RoomStackReservationPlatform.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {
    private final InventoryService inventoryService;

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name: {}", hotelDto.getName());
        HotelEntity hotelEntity = modelMapper.map(hotelDto, HotelEntity.class);

        // Always start new hotels as inactive, and ensure the non-null DB column is satisfied
        hotelEntity.setIsActive(false);

        hotelEntity = hotelRepository.save(hotelEntity);
        log.info("Creating a new hotel with id: {}", hotelEntity.getId());

        return modelMapper.map(hotelEntity, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting the hotel with id: {}", id);
        HotelEntity hotelEntity = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id:" + id));

        return modelMapper.map(hotelEntity, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Updating the hotel with id: {}", id);
        HotelEntity hotelEntity = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id:" + id));
        modelMapper.map(hotelDto, hotelEntity);
        hotelEntity.setId(id);
        hotelRepository.save(hotelEntity);

        return modelMapper.map(hotelEntity, HotelDto.class);
    }

    @Override
    public void deleteHotelById(Long id) {
        boolean exists = hotelRepository.existsById(id);
        if(!exists)
            throw new ResourceNotFoundException("Hotel not found with ID: " + id);

        hotelRepository.deleteById(id);
        // TODO:- delete the future inventories for this hotel
    }

    @Override
    public void activateHotel(Long id) {
        log.info("Activating the hotel with id: {}", id);
        HotelEntity hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: "+id));
        hotel.setIsActive(true);

        // Assuming only do it once
        for(RoomEntity room: hotel.getRooms()) {
            inventoryService.initializeRoomForAYear(room);
        }
    }
}