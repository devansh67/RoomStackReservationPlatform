package com.devcodes.projects.RoomStackReservationPlatform.service;

import com.devcodes.projects.RoomStackReservationPlatform.dto.RoomDto;
import com.devcodes.projects.RoomStackReservationPlatform.entity.HotelEntity;
import com.devcodes.projects.RoomStackReservationPlatform.entity.RoomEntity;
import com.devcodes.projects.RoomStackReservationPlatform.exceptions.ResourceNotFoundException;
import com.devcodes.projects.RoomStackReservationPlatform.repository.HotelRepository;
import com.devcodes.projects.RoomStackReservationPlatform.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        log.info("Creating a new room in hotel with id: {}", hotelId);
        HotelEntity hotelEntity = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id:" + hotelId));
        RoomEntity room = modelMapper.map(roomDto, RoomEntity.class);
        room.setHotel(hotelEntity);
        room = roomRepository.save(room);

        if(hotelEntity.getIsActive()) {
            inventoryService.initializeRoomForAYear(room);
        }

        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("Getting all rooms in hotel with id: {}", hotelId);
        HotelEntity hotelEntity = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id:" + hotelId));

        return hotelEntity.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Getting rooms with id: {}", roomId);
        RoomEntity room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id:" + roomId));

        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public void deleteRoomById(Long roomId) {
        log.info("Deleting room with id: {}", roomId);
        boolean exists = roomRepository.existsById(roomId);

        if(!exists) {
            throw new ResourceNotFoundException("Room not found with id: " + roomId);
        }

        roomRepository.deleteById(roomId);

        // TODO:- Delete all the related future inventory for this room
    }
}
