package com.devcodes.projects.RoomStackReservationPlatform.controller;

import com.devcodes.projects.RoomStackReservationPlatform.dto.BookingDto;
import com.devcodes.projects.RoomStackReservationPlatform.dto.BookingRequest;
import com.devcodes.projects.RoomStackReservationPlatform.dto.GuestDto;
import com.devcodes.projects.RoomStackReservationPlatform.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {

    private final BookingService bookingService;

    @PostMapping("/init")
    public ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequest bookingRequest) {
        return ResponseEntity.ok(bookingService.initialiseBooking(bookingRequest));
    }

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId,
                                                @RequestBody List<GuestDto> guestDtoList) {
        return ResponseEntity.ok(bookingService.addGuests(bookingId, guestDtoList));
    }
}
