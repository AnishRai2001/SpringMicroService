package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Bookings;


public interface BookingRepository extends JpaRepository<Bookings, Long> {

}