package com.jvb_intern.rental_acommodation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jvb_intern.rental_acommodation.entity.Post;

public interface HomeService {
    // Tìm kiếm bài đăng
    Page<Post> searchPost(String keyword, Pageable pageable);

    // Tìm kiếm bài đăng theo email
    Page<Post> searchPostByEmail(String email, Pageable pageable);

    // Lấy ra danh sách bài đăng
    Page<Post> listPost(Pageable pageable);
}
