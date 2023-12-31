package com.jvb_intern.rental_acommodation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jvb_intern.rental_acommodation.dto.DisplayPostDto;
import com.jvb_intern.rental_acommodation.dto.LandlordInfoDto;
import com.jvb_intern.rental_acommodation.entity.Landlord;
import com.jvb_intern.rental_acommodation.entity.Post;
import com.jvb_intern.rental_acommodation.repository.LandlordRepository;
import com.jvb_intern.rental_acommodation.repository.PostRepository;
import com.jvb_intern.rental_acommodation.service.LandlordHomeService;

@Service
public class LandlordHomeServiceImpl implements LandlordHomeService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LandlordRepository landlordRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    // Search bài đăng theo email
    @Override
    public Page<Post> searchPostByEmail(String email, Pageable pageable) {
        return postRepository.findPostByEmail(email, pageable);
    }

    // Sửa thông tin cá nhân của người dùng có tài khoản là email
    @Override
    public void saveChangeInfo(String email, LandlordInfoDto landlordInfoDto) {
        Landlord userLandlord = landlordRepository.findByLandlordEmail(email);
        
        // Nếu trùng với username cũ thì không cần cập nhật
        Boolean isUpdate = false;
        if(landlordInfoDto.getName() != null && !landlordInfoDto.getName().equals(userLandlord.getName())) {
            userLandlord.setName(landlordInfoDto.getName());
            isUpdate = true;
        }

        if(landlordInfoDto.getPhone() != null && !landlordInfoDto.getName().equals(userLandlord.getName())) {
            userLandlord.setName(landlordInfoDto.getPhone());
            isUpdate = true;
        }

        String password = landlordInfoDto.getPassword();
        String confirmPassword = landlordInfoDto.getConfirmPassword();
        if(password != null && confirmPassword != null && password.equals(confirmPassword)) {
            String hashedPassword = passwordEncoder.encode(landlordInfoDto.getPassword());
            userLandlord.setPassword(hashedPassword);
            isUpdate = true;
        }

        if(isUpdate) {
            landlordRepository.save(userLandlord);
        }
    }

    @Override
    public DisplayPostDto findbyPostId(Long Id) {
        Post post = postRepository.findByPostId(Id);

        DisplayPostDto displayPostDto = new DisplayPostDto();
        displayPostDto.setTitle(post.getTitle());
        displayPostDto.setContent(post.getContent());
        displayPostDto.setPhoto(post.getPhoto());
        displayPostDto.setAddress(post.getAccommodate().getAddress());
        displayPostDto.setPriceCategory(post.getAccommodate().getPriceCategory());
        displayPostDto.setSquare(post.getAccommodate().getSquare());
        displayPostDto.setArea(post.getAccommodate().getArea());
        displayPostDto.setWifi(post.getAccommodate().getWifi());
        displayPostDto.setConditioner(post.getAccommodate().getConditioner());
        displayPostDto.setParking(post.getAccommodate().getParking());
        displayPostDto.setPhone(post.getLandlord().getPhone());
        displayPostDto.setRoomPrice(post.getAccommodate().getRoomPrice());
        displayPostDto.setEmail(post.getLandlord().getLandlordEmail());
        displayPostDto.setName(post.getLandlord().getName());

        return displayPostDto;
    }
}
