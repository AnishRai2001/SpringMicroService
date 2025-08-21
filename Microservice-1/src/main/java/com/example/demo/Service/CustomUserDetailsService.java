package com.example.demo.Service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	   @Autowired
	    private EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String email ) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Employee emp =employeeRepository.findByEmail( email)
				.orElseThrow(()-> new UsernameNotFoundException("user not found with a given id"));
		return new User(
				emp.getEmail(),
				emp.getPassword(),
				Collections.singleton(new SimpleGrantedAuthority("Role"+ emp.getRole())));
				
	}

}
