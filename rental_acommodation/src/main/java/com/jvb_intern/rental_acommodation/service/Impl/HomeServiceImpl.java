package com.jvb_intern.rental_acommodation.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jvb_intern.rental_acommodation.entity.Post;
import com.jvb_intern.rental_acommodation.repository.PostRepository;
import com.jvb_intern.rental_acommodation.service.HomeService;

@Service
@Primary
public class HomeServiceImpl implements HomeService{
    @Autowired
    private PostRepository postRepository;

    // Search bài đăng theo keyword
    @Override
    public Page<Post> searchPost(String keyword, Pageable pageable) {
        keyword = keyword.toLowerCase();
        String[] mainKeyword = {"rẻ", "khép kín", "hiện đại", "ở ngay", 
                                "ở luôn", "trung tâm", "công nghệ", "sư phạm", 
                                "ngoại ngữ", "hust", "hus", "neu", "khoa học tự nhiên", 
                                "thuỷ lợi", "bách khoa", "phát triển", "hiện đại", 
                                "phố cổ", "tháp rùa", "hồ tây", "nhân văn", 
                                "hoàng thành", "lăng bác", "ngân hàng", "wifi", "điều hoà", "nóng lạnh", 
                                "bãi đỗ xe", "thanh xuân", "cầu giấy", "hà đông", "đống đa", "hai bà trưng", "hoàn kiếm"};

        for (String word : mainKeyword) {
            if(keyword.contains(word)) {
                keyword = word;
            }
        }
        return postRepository.findPostByKeyword(keyword, pageable);
    }

    // Search bài đăng theo email
    @Override
    public Page<Post> searchPostByEmail(String email, Pageable pageable) {
        return postRepository.findPostByEmail(email, pageable);
    }

    @Override
    public Page<Post> listPost(Pageable pageable) {
        return postRepository.findAll(pageable);
    } 

    public String convertTextToHtml(String plainText) {
        Document doc = new Document("");
        Element p = doc.appendElement("p");
        Element span = p.appendElement("span")
            .attr("style", "font-family: 'Roboto Regular', Roboto, Arial; font-size: 14px; background-color: #ffffff;")
            .text(plainText);
        return doc.outerHtml();
    }
}
