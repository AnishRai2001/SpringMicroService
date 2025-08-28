package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.PropertyDto;
import com.example.demo.service.PropertyService;
import com.example.demo.structure.ResponseStructure;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping(
        value = "/add-property",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseStructure<PropertyDto>> addProperty(
            @RequestParam("property") String propertyJson,
            @RequestParam("files") MultipartFile[] files) {

        ObjectMapper objectMapper = new ObjectMapper();
        PropertyDto dto;

        try {
            dto = objectMapper.readValue(propertyJson, PropertyDto.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        PropertyDto savedProperty = propertyService.addProperty(dto, files);

        ResponseStructure<PropertyDto> response = new ResponseStructure<>();
        response.setMessage("Property added");
        response.setStatus(201);
        response.setData(savedProperty);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

	@GetMapping("/search")
	public ResponseStructure searchProperty(
	        @RequestParam String name,
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
	) {
	    ResponseStructure response = propertyService.searchProperty(name, date);
	    return response;
	}
}