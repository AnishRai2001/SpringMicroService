package com.example.demo.Service;

import java.io.IOException;
import java.security.Security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtFilter extends OncePerRequestFilter{
	
	 private JwtService jwtService;    
	   private CustomUserDetailsService userDetailsService; // ✅
	    

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String authHeader = request.getHeader("Authorization");
		String email=null;
		
		if(authHeader !=null && authHeader.startsWith("Bearer")) {
			String token= authHeader.substring(7);
			 email=jwtService.validateTokenAndGetSubject(token);
		}
		 if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			 UserDetails userDetails=userDetailsService.loadUserByUsername(email);
			 
			  UsernamePasswordAuthenticationToken authentication =
	                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

	            SecurityContextHolder.getContext().setAuthentication(authentication);
	        }

	        filterChain.doFilter(request, response);
		}
		
	}


