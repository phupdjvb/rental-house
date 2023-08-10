package com.jvb_intern.rental_acommodation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jvb_intern.rental_acommodation.entity.Post;
import com.jvb_intern.rental_acommodation.repository.PostRepository;
import com.jvb_intern.rental_acommodation.service.LandlordHomeService;

@Service
public class LandlordHomeServiceImpl implements LandlordHomeService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public Page<Post> listPost(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

}
