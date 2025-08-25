package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enity.State;
import com.example.demo.service.StateService;
import com.example.demo.structure.ResponseStructure;

@RestController
@RequestMapping("/api/v1/state")
public class StateController {

    @Autowired
    private StateService stateService;

    @PostMapping("/add")
    public ResponseEntity<ResponseStructure<String>> saveState(@RequestBody State state) {
        ResponseStructure<String> response = new ResponseStructure<>();
        stateService.saveState(state);

        response.setStatus(201);
        response.setMessage("Area saved successfully");
        response.setData(state.getName());

        return ResponseEntity.status(201).body(response);
    }
}