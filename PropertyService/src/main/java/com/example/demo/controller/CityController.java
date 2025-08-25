package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enity.City;
import com.example.demo.enity.State;
import com.example.demo.service.CityService;
import com.example.demo.service.StateService;
import com.example.demo.structure.ResponseStructure;

@RestController
@RequestMapping("/api/v1/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping("/add")
    public ResponseEntity<ResponseStructure<String>> saveCity(@RequestBody City city) {
        ResponseStructure<String> response = new ResponseStructure<>();
        cityService.saveCity(city);

        response.setStatus(201);
        response.setMessage("Area saved successfully");
        response.setData(city.getName());

        return ResponseEntity.status(201).body(response);
    }
}