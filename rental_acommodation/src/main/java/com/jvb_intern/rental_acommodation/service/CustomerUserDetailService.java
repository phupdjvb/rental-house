package com.jvb_intern.rental_acommodation.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jvb_intern.rental_acommodation.entity.Landlord;
import com.jvb_intern.rental_acommodation.entity.Tenant;
import com.jvb_intern.rental_acommodation.repository.LandlordRepository;
import com.jvb_intern.rental_acommodation.repository.TenantRepository;

@Service
public class CustomerUserDetailService implements UserDetailsService {
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private LandlordRepository landlordRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Tenant tenant = tenantRepository.findByTenantEmail(email);
        Landlord landlord = landlordRepository.findByLandlordEmail(email);

        if (email.equals("admin@gmail.com")) {
            String password = "123456";
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
            return new org.springframework.security.core.userdetails.User(email, password, authorities);
        }
        
        if(tenant == null && landlord == null) {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản trong hệ thống");
        } else {
            if(tenant != null && landlord == null) {
                List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("TENANT"));
                return new org.springframework.security.core.userdetails.User(tenant.getTenantEmail(), tenant.getPassword(), authorities);
            } else if(tenant == null && landlord != null) {
                List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("LANDLORD"));
                return new org.springframework.security.core.userdetails.User(landlord.getLandlordEmail(), landlord.getPassword(), authorities);
            } else {
                throw new UsernameNotFoundException("Tài khoản không hợp lệ");
            }
        }

    }
}