package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.client.PropertyClient;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.BookingDto;
import com.example.demo.dto.PropertyDto;
import com.example.demo.dto.RoomAvailability;
import com.example.demo.dto.Rooms;
import com.example.demo.entity.BookingDate;
import com.example.demo.entity.Bookings;
import com.example.demo.repository.BookingDateRepository;
import com.example.demo.repository.BookingRepository;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

	
	@Autowired
	private PropertyClient propertyClient;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private BookingDateRepository bookingDateRepository;

	
	@PostMapping("/add-to-cart")
	public ApiResponse<List<String>> cart(@RequestBody BookingDto bookingDto) {

	    ApiResponse<List<String>> apiResponse = new ApiResponse<>();
	    List<String> messages = new ArrayList<>();

	    ApiResponse<PropertyDto> response = propertyClient.getPropertyById(bookingDto.getPropertyId());
	    ApiResponse<Rooms> roomType = propertyClient.getRoomType(bookingDto.getRoomId());
	    ApiResponse<List<RoomAvailability>> totalRoomsAvailable = propertyClient.getTotalRoomsAvailable(bookingDto.getRoomAvailabilityId());
	    List<RoomAvailability> availableRooms = totalRoomsAvailable.getData();

	    // Logic to check available rooms based on date and count
	    for (LocalDate date : bookingDto.getDate()) {
	        boolean dateAvailable = false;

	        for (RoomAvailability room : availableRooms) {
	            if (room.getAvailableDate().equals(date) && room.getAvailableCount() > 0) {
	                dateAvailable = true;
	                break; // No need to check other rooms for this date
	            }
	        }

	        if (!dateAvailable) {
	            messages.add("Room not available on: " + date);
	            apiResponse.setMessage("Sold Out");
	            apiResponse.setStatus(500);
	            apiResponse.setData(messages);
	            return apiResponse; // Stop here if any date is unavailable
	        }
	    }

	    // All dates are available → save booking
	    Bookings bookings = new Bookings();
	    bookings.setName(bookingDto.getName());
	    bookings.setEmail(bookingDto.getEmail());
	    bookings.setMobile(bookingDto.getMobile());
	    bookings.setPropertyName(response.getData().getName());
	    bookings.setStatus("pending");
	    bookings.setTotalPrice(roomType.getData().getBasePrice() * bookingDto.getTotalNigths());
	    Bookings savedBooking = bookingRepository.save(bookings);

	    // Save booking dates
	    for (LocalDate date : bookingDto.getDate()) {
	        BookingDate bookingDate = new BookingDate();
	        bookingDate.setDate(date);
	        bookingDate.setBookings(savedBooking);
	        bookingDateRepository.save(bookingDate);
	    }

	    // Success response
	    messages.add("Booking added to cart successfully!");
	    apiResponse.setMessage("Success");
	    apiResponse.setStatus(200);
	    apiResponse.setData(messages);
	    return apiResponse;
	}
}