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

    // Search bài đăng theo keyword
    @Override
    public Page<Post> searchPost(String keyword, Pageable pageable) {
        keyword = keyword.toLowerCase();
        String[] mainKeyword = {"rẻ", "khép kín", "hiện đại", "ở ngay", 
                                "ở luôn", "trung tâm", "công nghệ", "sư phạm", 
                                "ngoại ngữ", "hust", "hus", "neu", "khoa học tự nhiên", 
                                "thuỷ lợi", "bách khoa", "phát triển", "hiện đại", 
                                "phố cổ", "tháp rùa", "hồ tây", "nhân văn", 
                                "hoàng thành", "lăng bác", "ngân hàng", "wifi", "điều hoà", "nóng lạnh", "bãi đỗ xe"};

        for (String word : mainKeyword) {
            if(keyword.contains(word)) {
                keyword = word;
            }
        }
        return postRepository.findPostByKeyword(keyword, pageable);
    }

}
