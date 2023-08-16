package com.jvb_intern.rental_acommodation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jvb_intern.rental_acommodation.dto.DisplayPostDto;
import com.jvb_intern.rental_acommodation.dto.LandlordInfoDto;
import com.jvb_intern.rental_acommodation.entity.Post;

public interface LandlordHomeService {
    Page<Post> listPost(Pageable pageable);

    // Tìm kiếm bài đăng
    Page<Post> searchPost(String keyword, Pageable pageable);

    // Tìm kiếm bài đăng theo email
    Page<Post> searchPostByEmail(String email, Pageable pageable);

    // Sửa thông tin cá nhân của người dùng có tài khoản email
    void saveChangeInfo(String email, LandlordInfoDto landlordInfoDto);

    // Tìm bài đăng theo id 
    DisplayPostDto findbyPostId(Long Id);
}
