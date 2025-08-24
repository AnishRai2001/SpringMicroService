package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
		    consumes = MediaType.MULTIPART_FORM_DATA_VALUE,  // Ensures the endpoint accepts multipart/form-data
		    produces = MediaType.APPLICATION_JSON_VALUE
		)
		public ResponseEntity<ResponseStructure> addProperty(
		        @RequestParam("property") String propertyJson,  // Use RequestParam to get the property as a raw JSON string
		        @RequestParam("files") MultipartFile[] files) {  // Use RequestParam to handle files
		
		
		  ObjectMapper objectMapper = new ObjectMapper();
		    PropertyDto dto = null;
		    try {
		        dto = objectMapper.readValue(propertyJson, PropertyDto.class);  // Convert JSON string to PropertyDto
		    } catch (JsonProcessingException e) {
		        logger.error("Error parsing property JSON", e);
		        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Handle bad JSON
		    }

		    
		    // Process the property and files
		    PropertyDto property = propertyService.addProperty(dto, files);

		    // Create response object
		    ResponseStructure<PropertyDto> response = new ResponseStructure<>();
		    response.setMessage("Property added");
		    response.setStatus(201);
		    response.setData(property);

		    return new ResponseEntity<>(response, HttpStatus.CREATED);
		}
}
