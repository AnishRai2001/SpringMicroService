package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.enity.PropertyPhotos;

public interface PropertyPhotoRepo extends JpaRepository<PropertyPhotos, Long> {

}
