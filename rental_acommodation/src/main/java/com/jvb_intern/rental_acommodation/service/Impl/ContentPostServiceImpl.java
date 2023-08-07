package com.jvb_intern.rental_acommodation.service.Impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jvb_intern.rental_acommodation.dto.ContentPostDto;
import com.jvb_intern.rental_acommodation.entity.Accommodate;
import com.jvb_intern.rental_acommodation.entity.Landlord;
import com.jvb_intern.rental_acommodation.entity.Post;
import com.jvb_intern.rental_acommodation.repository.AccommodateRepository;
import com.jvb_intern.rental_acommodation.repository.LandlordRepository;
import com.jvb_intern.rental_acommodation.service.ContentPostService;

@Service
public class ContentPostServiceImpl implements ContentPostService {
    @Autowired
    private AccommodateRepository accommodateRepository;

    @Autowired
    private LandlordRepository landlordRepository;
    
    /*Save post and accommodata in database and return "savedPost" object */
    @Override
    public Post savePostAndAccommodate(ContentPostDto newPostDto, String landlordEmail) {
        Post savedPost = savePost(newPostDto, landlordEmail);

        Accommodate accommodate = new Accommodate();

        // get data of accommodate from newPostDto
        accommodate.setRoomPrice(newPostDto.getRoomPrice());
        accommodate.setAddress(newPostDto.getAddress());
        accommodate.setArea(newPostDto.getArea());
        accommodate.setSquare(newPostDto.getSquare());
        accommodate.setRoomStatus(newPostDto.getRoomStatus());
        accommodate.setPriceCategory(newPostDto.getPriceCategory());
        accommodate.setWifi(newPostDto.getWifi());
        accommodate.setParking(newPostDto.getParking());
        accommodate.setConditioner(newPostDto.getConditioner());

        // Save relationship
        accommodate.setPost(savedPost);
        Landlord landlord = landlordRepository.findByLandlordEmail(landlordEmail);
        accommodate.setLandlord(landlord);

        // save data in table Accommodate
        accommodateRepository.save(accommodate);
        return savedPost;
    }

    /* Save post in database and return "newPost" object */
    @Override
    public Post savePost(ContentPostDto newPostDto, String landlordEmail) {
        Post newPost = new Post();

        // Get data from newPostDto
        newPost.setTitle(newPostDto.getTitle());
        newPost.setContent(newPostDto.getContent());
        newPost.setPhoto(newPostDto.getPhoto());
        newPost.setIsDeleted(false);
        
        Landlord landlord = landlordRepository.findByLandlordEmail(landlordEmail);
        newPost.setLandlord(landlord);
        
        // get current time
        LocalDateTime currentTime = LocalDateTime.now();
        newPost.setCreatedAt(currentTime);
        return newPost;
    }
}
