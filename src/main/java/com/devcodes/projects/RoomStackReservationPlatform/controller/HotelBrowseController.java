package com.devcodes.projects.RoomStackReservationPlatform.controller;

import com.devcodes.projects.RoomStackReservationPlatform.dto.HotelDto;
import com.devcodes.projects.RoomStackReservationPlatform.dto.HotelInfoDto;
import com.devcodes.projects.RoomStackReservationPlatform.dto.HotelSearchRequest;
import com.devcodes.projects.RoomStackReservationPlatform.service.HotelService;
import com.devcodes.projects.RoomStackReservationPlatform.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @PostMapping("/search")
    public ResponseEntity<Page<HotelDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest) {
        Page<HotelDto> page = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId) {
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }
}
