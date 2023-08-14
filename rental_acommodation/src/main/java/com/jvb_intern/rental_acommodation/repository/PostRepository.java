package com.jvb_intern.rental_acommodation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jvb_intern.rental_acommodation.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

    // Tìm kiếm bài đăng
    @Query("SELECT DISTINCT p FROM Post p" +
            " INNER JOIN p.accommodate a" +
            " WHERE (LOWER(p.title) LIKE %:keyword%)" +
            " OR (LOWER(a.priceCategory) LIKE %:keyword% OR LOWER(a.tag) LIKE %:keyword%)" +
            " OR (LOWER(a.address) LIKE %:keyword% OR LOWER(a.area) LIKE %:keyword%)" +
            " OR (LOWER(a.wifi) LIKE %:keyword%)" +
            " OR (LOWER(a.conditioner) LIKE %:keyword% OR LOWER(a.parking) LIKE %:keyword%)")
    Page<Post> findPostByKeyword(String keyword, Pageable pageable);
}
