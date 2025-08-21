package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="MICROSERVICE-2")
public interface Client1 {
	
	@GetMapping("/hello")
	public String getMessage();

}
