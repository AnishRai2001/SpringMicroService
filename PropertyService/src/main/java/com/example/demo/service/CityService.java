package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enity.City;
import com.example.demo.repository.CityRepository;


@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;
	
	public City saveCity(City city) {
		City saveCity=cityRepository.save(city);
		return saveCity;
	}


}
