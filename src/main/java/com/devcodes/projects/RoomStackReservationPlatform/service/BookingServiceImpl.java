package com.devcodes.projects.RoomStackReservationPlatform.service;

import com.devcodes.projects.RoomStackReservationPlatform.dto.BookingDto;
import com.devcodes.projects.RoomStackReservationPlatform.dto.BookingRequest;
import com.devcodes.projects.RoomStackReservationPlatform.dto.GuestDto;
import com.devcodes.projects.RoomStackReservationPlatform.entity.*;
import com.devcodes.projects.RoomStackReservationPlatform.entity.enums.BookingStatus;
import com.devcodes.projects.RoomStackReservationPlatform.entity.enums.Role;
import com.devcodes.projects.RoomStackReservationPlatform.exceptions.ResourceNotFoundException;
import com.devcodes.projects.RoomStackReservationPlatform.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final GuestRepository guestEntityRepository;

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BookingDto initialiseBooking(BookingRequest bookingRequest) {

        log.info("Initialising booking for hotel: {}, room: {}, date {} - {}", bookingRequest.getHotelId(), bookingRequest.getRoomId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());

        HotelEntity hotel = hotelRepository.findById(bookingRequest.getHotelId()).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: "+bookingRequest.getHotelId()));

        RoomEntity room = roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(() -> new ResourceNotFoundException("Room not found with id: "+bookingRequest.getRoomId()));

        List<InventoryEntity> inventoryList = inventoryRepository.findAndLockAvailableInventory(room.getId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(), bookingRequest.getRoomsCount());

        long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate()) + 1;

        if(inventoryList.size() != daysCount) {
            throw new IllegalStateException("Room is not available for the selected dates");
        }

        // Reserve the room / update the booked count of inventories
        for(InventoryEntity inventory: inventoryList) {
            inventory.setReservedCount(inventory.getReservedCount() + bookingRequest.getRoomsCount());
        }

        inventoryRepository.saveAll(inventoryList);

        UserEntity user = getDefaultUser();

        // TODO:- Calculate dynamic pricing amount

        // Create the booking
        BookingEntity booking = BookingEntity.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .user(user)
                .roomsCount(bookingRequest.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build();

        booking = bookingRepository.save(booking);
        return toDto(booking);
    }

    @Override
    @Transactional
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList) {
        log.info("Adding guests in the booking for bookingId: {}", bookingId);

        BookingEntity booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: "+bookingId));

        if(hasBookingExpired(booking)) {
         throw new IllegalStateException("Booking has already gotten expired");
        }

        if(booking.getBookingStatus() != BookingStatus.RESERVED) {
            throw new IllegalStateException("Booking is not under reserved state anymore, so guests cannot be added to the booking");
        }

        for(GuestDto guestDto: guestDtoList) {
            GuestEntity guest = modelMapper.map(guestDto, GuestEntity.class);
            guest.setUser(getDefaultUser());
            guest = guestEntityRepository.save(guest);
            booking.getGuests().add(guest);
        }

        booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
        booking = bookingRepository.save(booking);

        return modelMapper.map(booking, BookingDto.class);
    }

    private boolean hasBookingExpired(BookingEntity booking) {
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    private UserEntity getDefaultUser() {
        List<UserEntity> users = userRepository.findAll();
        if (!users.isEmpty()) {
            return users.getFirst();
        }

        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setName("Test User");
        user.setRoles(Set.of(Role.GUEST));
        return userRepository.save(user);
    }

    private UserEntity getCurrentUser() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        return user;
    }

    private BookingDto toDto(BookingEntity booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setHotelId(booking.getHotel().getId());
        dto.setRoomId(booking.getRoom().getId());
        dto.setUserId(booking.getUser().getId());
        dto.setRoomsCount(booking.getRoomsCount());
        dto.setCheckInDate(booking.getCheckInDate());
        dto.setCheckOutDate(booking.getCheckOutDate());
        dto.setAmount(booking.getAmount());
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setUpdatedAt(booking.getUpdatedAt());
        dto.setBookingStatus(booking.getBookingStatus());
        return dto;
    }
}
