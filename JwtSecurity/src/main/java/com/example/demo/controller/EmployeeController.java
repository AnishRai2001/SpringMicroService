
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.CustomUserDetailsService;
import com.example.demo.Service.JwtService;
import com.example.demo.ServiceImpl.EmployeeServiceImpl;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.LoginDto;
import com.example.demo.structure.ResponseStructure;

@RestController
@RequestMapping("/auth/v1")
public class EmployeeController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<String>> registerEmployee(@RequestBody EmployeeDto employeeDto) {
        // Directly get the structured response from service
        ResponseStructure<String> response = employeeService.registerEmployee(employeeDto);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<String>> loginEmployee(@RequestBody LoginDto loginDto) {
        ResponseStructure<String> response = new ResponseStructure<>();

        try {
            // Authenticate user credentials
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());

            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            
            // If successful, generate JWT token
            String token = jwtService.generateToken(loginDto.getEmail(),role);

            response.setMessage("Login successful");
            response.setSuccess(true);
            response.setData(token);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage("Invalid username or password");
            response.setSuccess(false);
            response.setData(null);

            return ResponseEntity.status(401).body(response);
        }
    }
}
