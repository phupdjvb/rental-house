package com.jvb_intern.rental_acommodation.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.jvb_intern.rental_acommodation.dto.ContentPostDto;
import com.jvb_intern.rental_acommodation.entity.Post;

public interface PostService {
    // Lấy ra các bài viết đã đăng
    Page<Post> getPosted(Authentication authTenant, Pageable pageable);

    // Láy bài viết theo Id
    ContentPostDto getPostById(Long postId);

    // Xoá bài đăng
    Post detelePost(Long postId);

    // Ngừng bán
    Post stopSelling(Long postId);

    // Danh sách bài đăng đã xoá
    Page<Post> getDeletedPost(Authentication authLandlord, Pageable pageable);

    // Cập nhật bài đăng có postId
    Post updatePost(Long postId, ContentPostDto postDto, Authentication authLandlord);

    // Đăng lại bài đăng
    Post repost(Long postId);
}
