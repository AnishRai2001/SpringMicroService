package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.enity.RoomAvailability;

public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability, Long> {

	public List<RoomAvailability> findByRoomId(long id);
}
