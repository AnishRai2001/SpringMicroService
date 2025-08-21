package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.enity.State;



public interface StateRepository extends JpaRepository<State, Long>  {
	State findByName(String name);
}