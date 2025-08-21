package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.enity.Area;



public interface AreaRepository extends JpaRepository<Area, Long>  {
	
	Area findByName(String name);

}