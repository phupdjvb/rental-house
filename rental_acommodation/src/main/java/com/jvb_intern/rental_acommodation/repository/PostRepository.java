package com.jvb_intern.rental_acommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jvb_intern.rental_acommodation.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{
}
