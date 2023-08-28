package com.jvb_intern.rental_acommodation.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.jvb_intern.rental_acommodation.dto.ContentPostDto;
import com.jvb_intern.rental_acommodation.entity.Accommodate;
import com.jvb_intern.rental_acommodation.entity.Post;
import com.jvb_intern.rental_acommodation.repository.AccommodateRepository;
import com.jvb_intern.rental_acommodation.repository.LandlordRepository;
import com.jvb_intern.rental_acommodation.repository.PostRepository;
import com.jvb_intern.rental_acommodation.service.PostService;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private LandlordRepository landlordRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccommodateRepository accommodateRepository;

    @Override
    public Page<Post> getPosted(Authentication authLandlord, Pageable pageable) {
        UserDetails userDetailsLandlord = (UserDetails) authLandlord.getPrincipal();

        String emailLandlord = userDetailsLandlord.getUsername();

        // Lấy ra id của landlord
        Long landlordId = landlordRepository.findByLandlordEmail(emailLandlord).getLandlordId();
        return landlordRepository.getPosted(landlordId, pageable);
    }

    @Override
    public ContentPostDto getPostById(Long postId) {
        Post post = postRepository.findByPostId(postId);
        ContentPostDto updatePost = new ContentPostDto();

        updatePost.setTitle(post.getTitle());
        updatePost.setContent(post.getContent());
        updatePost.setPhoto(post.getPhoto());
        updatePost.setTag(post.getAccommodate().getTag());
        updatePost.setAddress(post.getAccommodate().getAddress());
        updatePost.setArea(post.getAccommodate().getArea());
        updatePost.setSquare(post.getAccommodate().getSquare());
        updatePost.setPriceCategory(post.getAccommodate().getPriceCategory());
        updatePost.setRoomPrice(post.getAccommodate().getRoomPrice());
        updatePost.setWifi(post.getAccommodate().getWifi());
        updatePost.setConditioner(post.getAccommodate().getConditioner());
        updatePost.setParking(post.getAccommodate().getParking());
        return updatePost;
    }

    // Xoá bài đăng
    @Override
    public Post detelePost(Long postId) {
        Post existedPost = postRepository.findByPostId(postId);
        existedPost.setIsDeleted(true);
        postRepository.save(existedPost);
        return existedPost;
    }

    // Ngừng cho thuê
    @Override
    public Post stopSelling(Long postId) {
        Post existedPost = postRepository.findByPostId(postId);
        Accommodate accomOfPost = existedPost.getAccommodate();
        accomOfPost.setRoomStatus(false);
        accommodateRepository.save(accomOfPost);

        return existedPost;
    }

    // Bài đăng đã xoá
    @Override
    public Page<Post> getDeletedPost(Authentication authLandlord, Pageable pageable) {
        UserDetails userDetailsLandlord = (UserDetails) authLandlord.getPrincipal();
        String emailLandlord = userDetailsLandlord.getUsername();

        // Lấy ra id của landlord
        Long landlordId = landlordRepository.findByLandlordEmail(emailLandlord).getLandlordId();
        return landlordRepository.getdeletedPost(landlordId, pageable);
    }

    @Override
    public Post updatePost(Long postId, ContentPostDto postDto, Authentication authLandlord) {
        Post post = postRepository.findByPostId(postId);

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setPhoto(postDto.getPhoto());

        LocalDateTime currentTime = LocalDateTime.now();
        post.setUpdatedAt(currentTime);

        Accommodate postAccommodate = post.getAccommodate();

        postAccommodate.setAddress(postDto.getAddress());
        postAccommodate.setArea(postDto.getArea());
        postAccommodate.setConditioner((postDto.getConditioner()));
        postAccommodate.setParking(postDto.getParking());
        postAccommodate.setPriceCategory((postDto.getPriceCategory()));
        postAccommodate.setRoomPrice(postDto.getRoomPrice());
        postAccommodate.setSquare(postDto.getSquare());
        postAccommodate.setTag(postDto.getTag());
        postAccommodate.setWifi(postDto.getWifi());

        postRepository.save(post);
        return post;
    }

    @Override
    public Post repost(Long postId) {
        Post existedPost = postRepository.findByPostId(postId);
        existedPost.setIsDeleted(false);
        postRepository.save(existedPost);
        return existedPost;
    }
}
