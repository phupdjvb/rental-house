package com.jvb_intern.rental_acommodation.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jvb_intern.rental_acommodation.dto.ContentPostDto;
import com.jvb_intern.rental_acommodation.entity.Accommodate;
import com.jvb_intern.rental_acommodation.entity.Landlord;
import com.jvb_intern.rental_acommodation.entity.Post;
import com.jvb_intern.rental_acommodation.repository.AccommodateRepository;
import com.jvb_intern.rental_acommodation.repository.LandlordRepository;
import com.jvb_intern.rental_acommodation.repository.PostRepository;
import com.jvb_intern.rental_acommodation.service.ContentPostService;

@Service
public class ContentPostServiceImpl implements ContentPostService {
    @Autowired
    private AccommodateRepository accommodateRepository;

    @Autowired
    private LandlordRepository landlordRepository;

    @Autowired
    private PostRepository postRepository;

    /* Save post and accommodata in database and return "savedPost" object */
    @Override
    public Post savePostAndAccommodate(ContentPostDto newPostDto, String landlordEmail) {
        Post savedPost = savePost(newPostDto, landlordEmail);

        Accommodate accommodate = new Accommodate();

        // get data of accommodate from newPostDto
        accommodate.setRoomPrice(newPostDto.getRoomPrice());
        accommodate.setAddress(newPostDto.getAddress());
        accommodate.setSquare(newPostDto.getSquare());
        accommodate.setRoomStatus(true);
        accommodate.setPriceCategory(newPostDto.getPriceCategory());
        accommodate.setWifi(newPostDto.getWifi());
        accommodate.setParking(newPostDto.getParking());
        accommodate.setConditioner(newPostDto.getConditioner());
        accommodate.setArea(newPostDto.getArea());
        accommodate.setTag(newPostDto.getTag());

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
        // Create new post
        Post post = new Post();

        // Get data from newPostDto
        post.setTitle(newPostDto.getTitle());
        post.setContent(newPostDto.getContent());
        post.setPhoto(newPostDto.getPhoto());
        post.setIsDeleted(false);

        Landlord landlord = landlordRepository.findByLandlordEmail(landlordEmail);
        post.setLandlord(landlord);

        // get current time
        LocalDateTime currentTime = LocalDateTime.now();
        post.setCreatedAt(currentTime);
        postRepository.save(post);
        return post;
    }

    
    @Override
    public Post updatePost(ContentPostDto updatePost, String landlordEmail, Long postId) {
        Post post = postRepository.findByPostId(postId);

        post.setTitle(updatePost.getTitle());
        post.setContent(updatePost.getContent());
        post.setPhoto(updatePost.getPhoto());
        post.setIsDeleted(false);

        Landlord landlord = landlordRepository.findByLandlordEmail(landlordEmail);
        post.setLandlord(landlord);

        // get current time
        LocalDateTime currentTime = LocalDateTime.now();
        post.setUpdatedAt(currentTime);
        postRepository.save(post);
        return post;
    }
}
