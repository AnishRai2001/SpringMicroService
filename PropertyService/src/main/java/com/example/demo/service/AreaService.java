package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enity.Area;
import com.example.demo.repository.AreaRepository;

@Service
public class AreaService {
	
	@Autowired
	private AreaRepository areaRepository;
	
	public Area saveArea(Area area) {
		Area savearea=areaRepository.save(area);
		return savearea;
	}
}
