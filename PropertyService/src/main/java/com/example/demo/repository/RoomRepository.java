package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.enity.Rooms;
@Repository
public interface RoomRepository extends JpaRepository<Rooms, Long>{

	Rooms save(Rooms rooms);

}
