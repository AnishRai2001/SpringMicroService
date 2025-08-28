package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.constant.AppConstants;
import com.example.demo.dto.EmailRequest;
import com.example.demo.dto.PropertyDto;
import com.example.demo.dto.RoomsDto;
import com.example.demo.enity.Area;
import com.example.demo.enity.City;
import com.example.demo.enity.Property;
import com.example.demo.enity.PropertyPhotos;
import com.example.demo.enity.Rooms;
import com.example.demo.enity.State;
import com.example.demo.repository.AreaRepository;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.PropertyPhotoRepo;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.repository.RoomAvailabilityRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.StateRepository;
import com.example.demo.structure.ResponseStructure;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomAvailabilityRepository availabilityRepository;

    @Autowired
    private PropertyPhotoRepo photosRepo;

    @Autowired
    private EmailProducer emailProducer;

    @Autowired
    private S3Service s3Service;

    public PropertyDto addProperty(PropertyDto dto, MultipartFile[] files) {
        Area area = areaRepository.findByName(dto.getArea());
        City city = cityRepository.findByName(dto.getCity());
        State state = stateRepository.findByName(dto.getState());

        Property property = new Property();
        property.setName(dto.getName());
        property.setNumberOfBathrooms(dto.getNumberOfBathrooms());
        property.setNumberOfBeds(dto.getNumberOfBeds());
        property.setNumberOfRooms(dto.getNumberOfRooms());
        property.setNumberOfGuestAllowed(dto.getNumberOfGuestAllowed());
        property.setArea(area);
        property.setCity(city);
        property.setState(state);

        Property savedProperty = propertyRepository.save(property);

        // Save rooms
        for (RoomsDto roomsDto : dto.getRooms()) {
            Rooms rooms = new Rooms();
            rooms.setProperty(savedProperty);
            rooms.setRoomType(roomsDto.getRoomType());
            rooms.setBasePrice(roomsDto.getBasePrice());
            roomRepository.save(rooms);
        }

        // Send email via Kafka
        EmailRequest request = new EmailRequest(
            "anishrai146@gmail.com",
            "Property added successfully",
            "Your property details are live"
        );
        emailProducer.sendEmail(request);

        // Upload files to S3 and save to DB
        List<String> fileUrls = s3Service.uploadFiles(files);
        for (String url : fileUrls) {
            PropertyPhotos photo = new PropertyPhotos();
            photo.setProperty(savedProperty);
            photo.setUrl(url);
            photosRepo.save(photo);
        }

        // Add image URLs to DTO for response
        dto.setImageUrls(fileUrls);

        return dto;
    }

	public ResponseStructure searchProperty(String city, LocalDate date) {
		List<Property> properties = propertyRepository.searchProperty(city,date);
		ResponseStructure<List<Property>> response = new ResponseStructure<>();
		
		response.setMessage("Search result");
		response.setStatus(200);
		response.setData(properties);
		
		return response;
	}

    }

