package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.enity.Property;
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>{

	Property save(Property property);

}
