package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enity.State;
import com.example.demo.repository.StateRepository;


@Service
public class StateService {
	
	@Autowired
	private StateRepository stateRepository;
	
	public State saveState(State state) {
		State saveState=stateRepository.save(state);
		return saveState;
	}

}
