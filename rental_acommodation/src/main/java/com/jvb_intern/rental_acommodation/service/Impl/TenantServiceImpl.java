package com.jvb_intern.rental_acommodation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jvb_intern.rental_acommodation.common.Constant;
import com.jvb_intern.rental_acommodation.dto.RegistrationDto;
import com.jvb_intern.rental_acommodation.entity.Tenant;
import com.jvb_intern.rental_acommodation.repository.TenantRepository;
import com.jvb_intern.rental_acommodation.service.TenantService;

@Service
public class TenantServiceImpl implements TenantService {
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Contructor
    public TenantServiceImpl(TenantRepository tenantRepository, PasswordEncoder passwordEncoder) {
        this.tenantRepository = tenantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveTenant(RegistrationDto registrationDto) {
        if (!registrationDto.getRole().equals(Constant.ROLE_TENANT)) {
            return;
        }

        Tenant newTenant = new Tenant();
        newTenant.setName(registrationDto.getName());
        newTenant.setTenantEmail(registrationDto.getEmail());
        newTenant.setPhone(registrationDto.getPhone());
        newTenant.setRole(registrationDto.getRole());

        // Mã hoá mật khẩu
        String hashedPassword = passwordEncoder.encode(registrationDto.getPassword());
        newTenant.setPassword(hashedPassword);
        tenantRepository.save(newTenant);
    }

    /* Tìm email */
    @Override
    public Tenant findByTenantEmail(String email) {
        return tenantRepository.findByTenantEmail(email);
    }

    /* Cập nhật password */
    @Override
    public void updatePassword(Tenant tenant, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        tenant.setPassword(encodedPassword);

        tenant.setResetPasswordToken(null);
        tenantRepository.save(tenant);
    }

    /* Cập nhật token khi reset mật khẩu */
    @Override
    public void updateResetPasswordToken(String token, String email) {
        Tenant tenant = tenantRepository.findByTenantEmail(email);
        if (tenant != null) {
            tenant.setResetPasswordToken(token);
            tenantRepository.save(tenant);
        } else {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản trong hệ thống");
        }
    }

    /* Tìm thông qua token */
    @Override
    public Tenant findByResetPasswordToken(String token) {
        return tenantRepository.findByResetPasswordToken(token);
    }

    /* Check mail tồn tại */
    @Override
    public boolean existByEmail(String email) {
        return tenantRepository.findByTenantEmail(email) != null;
    }

    /* Check token tồn tại */
    @Override
    public boolean existByResetPasswordToken(String token) {
        return tenantRepository.findByResetPasswordToken(token) != null;
    }
}