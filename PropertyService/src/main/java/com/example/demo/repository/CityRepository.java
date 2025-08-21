package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.enity.City;

public interface CityRepository extends JpaRepository<City, Long>  {
	City findByName(String name);
}