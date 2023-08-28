package com.jvb_intern.rental_acommodation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jvb_intern.rental_acommodation.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

    // Tìm kiếm bài đăng
    @Query("SELECT p FROM Post p" +
            " JOIN p.accommodate a" +
            " WHERE (LOWER(p.title) LIKE %:keyword%)" +
            " OR (LOWER(a.priceCategory) LIKE %:keyword% OR LOWER(a.tag) LIKE %:keyword%)" +
            " OR (LOWER(a.address) LIKE %:keyword% OR LOWER(a.area) LIKE %:keyword%)" +
            " OR (LOWER(a.wifi) LIKE %:keyword%)" +
            " OR (LOWER(a.conditioner) LIKE %:keyword% OR LOWER(a.parking) LIKE %:keyword%)")
    Page<Post> findPostByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // Truy vấn bài đăng theo email của landlord
    @Query("SELECT p FROM Post p" +
            " INNER JOIN p.landlord l" +
            " WHERE l.landlordEmail = ?1")
    Page<Post> findPostByEmail(String email, Pageable pageable);

    // Tìm post thông qua ID
    Post findByPostId(Long postId);

    // Tìm trong content
    @Query("SELECT p FROM Post p" +
            " JOIN p.accommodate a" +
            " WHERE (LOWER(p.content) LIKE %:keyword%)")
    Page<Post> findPostByContent(@Param("keyword") String keyword, Pageable pageable);


}
