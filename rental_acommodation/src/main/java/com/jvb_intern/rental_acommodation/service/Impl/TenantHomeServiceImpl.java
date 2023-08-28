package com.jvb_intern.rental_acommodation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jvb_intern.rental_acommodation.dto.LandlordInfoDto;
import com.jvb_intern.rental_acommodation.dto.TenantInfoDto;
import com.jvb_intern.rental_acommodation.entity.Landlord;
import com.jvb_intern.rental_acommodation.entity.Post;
import com.jvb_intern.rental_acommodation.entity.Tenant;
import com.jvb_intern.rental_acommodation.repository.TenantRepository;
import com.jvb_intern.rental_acommodation.service.HomeService;
import com.jvb_intern.rental_acommodation.service.TenantHomeService;

@Service
public class TenantHomeServiceImpl implements TenantHomeService {
    @Autowired
    private final HomeService homeService;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TenantHomeServiceImpl(@Qualifier("homeServiceImpl") HomeService homeService) {
        this.homeService = homeService;
    }

    // Tìm kiếm bài đăng
    @Override
    public Page<Post> searchPost(String keyword, Pageable pageable) {
        return homeService.searchPost(keyword, pageable);
    }

    // Danh sách bài dăng đã đăng của người dùng
    @Override
    public Page<Post> searchPostByEmail(String email, Pageable pageable) {
       return homeService.searchPostByEmail(email, pageable);
    }

    // Lấy ra danh sách bài đăng
    @Override
    public Page<Post> listPost(Pageable pageable) {
        return homeService.listPost(pageable);
    }

    // Lưu thông tin thay đổi
    @Override
    public void saveChangeInfo(String email, TenantInfoDto tenantInfoDto) {
        Tenant tenantUser = tenantRepository.findByTenantEmail(email);
        
        // Nếu trùng với username cũ thì không cần cập nhật
        Boolean isUpdate = false;
        if(tenantInfoDto.getName() != null && !tenantInfoDto.getName().equals(tenantUser.getName())) {
            tenantUser.setName(tenantInfoDto.getName());
            isUpdate = true;
        }

        if(tenantInfoDto.getPhone() != null && !tenantInfoDto.getName().equals(tenantUser.getName())) {
            tenantUser.setName(tenantInfoDto.getPhone());
            isUpdate = true;
        }

        String password = tenantInfoDto.getPassword();
        String confirmPassword = tenantInfoDto.getConfirmPassword();
        if(password != null && confirmPassword != null && password.equals(confirmPassword)) {
            String hashedPassword = passwordEncoder.encode(tenantInfoDto.getPassword());
            tenantUser.setPassword(hashedPassword);
            isUpdate = true;
        }

        if(isUpdate) {
           tenantRepository.save(tenantUser);
        }
    }
}
