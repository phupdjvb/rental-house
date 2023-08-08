package com.jvb_intern.rental_acommodation.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jvb_intern.rental_acommodation.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllCreatedAt(LocalDateTime createdAt, Pageable pageable);
}
