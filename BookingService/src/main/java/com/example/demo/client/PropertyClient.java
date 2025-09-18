package com.example.demo.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.PropertyDto;
import com.example.demo.dto.RoomAvailability;
import com.example.demo.dto.Rooms;

@FeignClient(name = "PROPERTYSERVICE") 
public interface PropertyClient {
	
	@GetMapping("/api/v1/property/property-id")
	public ApiResponse<PropertyDto> getPropertyById(@RequestParam long id);
	
	@GetMapping("/api/v1/property/room-available-room-id")
	public ApiResponse<List<RoomAvailability>> getTotalRoomsAvailable(@RequestParam long id);


	@GetMapping("/api/v1/property/room-id")
	public ApiResponse<Rooms> getRoomType(@RequestParam long id);

}
