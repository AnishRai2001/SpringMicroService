package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.client.AuthServiceFeignClient;
import com.example.demo.client.Client1;
import com.example.demo.dto.EmployeeDto;

@RestController
@RequestMapping("/demo")
public class DemoController {
	@Autowired
	Client1 client1;

    @Autowired
    private AuthServiceFeignClient authServiceFeignClient;

	@GetMapping("/welcome")
	public String getMessage(String message) {
		return client1.getMessage();
	}
	 @GetMapping("/user-by-email/{email}")
	    public ResponseEntity<EmployeeDto> getUserByEmail(
	            @PathVariable String email,
	            @RequestHeader("Authorization") String token) {

	        EmployeeDto employeeDto = authServiceFeignClient.getUserByEmail(email, token);
	        return ResponseEntity.ok(employeeDto);
	    }
}
