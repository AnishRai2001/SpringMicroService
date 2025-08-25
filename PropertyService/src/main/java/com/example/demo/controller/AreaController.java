package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.enity.Area;
import com.example.demo.service.AreaService;
import com.example.demo.structure.ResponseStructure;

@RestController
@RequestMapping("/api/v1/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @PostMapping("/add")
    public ResponseEntity<ResponseStructure<String>> saveArea(@RequestBody Area area) {
        ResponseStructure<String> response = new ResponseStructure<>();
        areaService.saveArea(area);

        response.setStatus(201);
        response.setMessage("Area saved successfully");
        response.setData(area.getName());

        return ResponseEntity.status(201).body(response);
    }
}
