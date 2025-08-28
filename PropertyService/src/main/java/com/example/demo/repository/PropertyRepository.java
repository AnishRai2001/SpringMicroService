package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.enity.Property;
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>{

	//Property save(Property property);
	@Query("""
		    SELECT DISTINCT p 
		    FROM Property p
		    JOIN p.rooms r
		    JOIN RoomAvailability ra ON ra.room = r
		    WHERE (
		        LOWER(p.city.name) LIKE LOWER(CONCAT('%', :name, '%')) OR
		        LOWER(p.area.name) LIKE LOWER(CONCAT('%', :name, '%')) OR
		        LOWER(p.state.name) LIKE LOWER(CONCAT('%', :name, '%'))
		    )
		    AND ra.availableDate = :date
		""")
		List<Property> searchProperty(@Param("name") String name, @Param("date") LocalDate date);

}
