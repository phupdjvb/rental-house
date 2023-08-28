package com.jvb_intern.rental_acommodation.service;

import com.jvb_intern.rental_acommodation.dto.ContentPostDto;
import com.jvb_intern.rental_acommodation.entity.Post;

public interface ContentPostService {
    Post savePostAndAccommodate(ContentPostDto newPostDto, String landlordEmail);

    Post savePost(ContentPostDto newPostDto, String landlordEmail);

    Post updatePost(ContentPostDto updatePost, String landlordEmail, Long postId);
}
