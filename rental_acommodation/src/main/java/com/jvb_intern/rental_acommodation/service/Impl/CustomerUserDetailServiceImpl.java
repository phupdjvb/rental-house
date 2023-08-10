package com.jvb_intern.rental_acommodation.service.impl;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jvb_intern.rental_acommodation.common.Constant;
import com.jvb_intern.rental_acommodation.entity.Landlord;
import com.jvb_intern.rental_acommodation.entity.Tenant;
import com.jvb_intern.rental_acommodation.repository.LandlordRepository;
import com.jvb_intern.rental_acommodation.repository.TenantRepository;
import com.jvb_intern.rental_acommodation.service.CustomerUserDetailService;

@Service
public class CustomerUserDetailServiceImpl implements CustomerUserDetailService {
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private LandlordRepository landlordRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Tenant tenant = tenantRepository.findByTenantEmail(email);
            if (tenant != null) {
                return new org.springframework.security.core.userdetails.User(
                        tenant.getTenantEmail(),
                        tenant.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority(Constant.ROLE_TENANT)));
            }

            Landlord landlord = landlordRepository.findByLandlordEmail(email);
            if (landlord != null) {
                return new org.springframework.security.core.userdetails.User(
                        landlord.getLandlordEmail(),
                        landlord.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority(Constant.ROLE_LANDLORD)));
            }

            if (Constant.EMAIL_ADMIN.equals(email)) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodePassword = passwordEncoder.encode(Constant.PASSWORD_ADMIN);
                return new org.springframework.security.core.userdetails.User(
                        email,
                        encodePassword,
                        Collections.singletonList(new SimpleGrantedAuthority(Constant.ROLE_ADMIN)));
            }

            throw new UsernameNotFoundException("Tài khoản không tồn tại trong hệ thống!");
        } catch (Exception e) {
            throw new UsernameNotFoundException("Đã xảy ra lỗi trong quá trình xác thực người dùng!");
        }
    }
}