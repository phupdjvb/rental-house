package com.jvb_intern.rental_acommodation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jvb_intern.rental_acommodation.entity.Post;

public interface LandlordHomeService {
    Page<Post> listPost(Pageable pageable);

    // Tìm kiếm bài đăng
    Page<Post> searchPost(String keyword, Pageable pageable);
}
