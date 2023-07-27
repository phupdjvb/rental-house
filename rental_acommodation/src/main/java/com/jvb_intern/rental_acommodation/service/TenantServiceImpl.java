package com.jvb_intern.rental_acommodation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jvb_intern.rental_acommodation.dto.TenantDto;
import com.jvb_intern.rental_acommodation.entity.Tenant;
import com.jvb_intern.rental_acommodation.repository.TenantRepository;

@Service
public class TenantServiceImpl implements TenantService {
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Contructor
    public TenantServiceImpl(TenantRepository tenantRepository, PasswordEncoder passwordEncoder) {
        this.tenantRepository = tenantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveTenant(TenantDto tenantDto) {
        Tenant newTenant = new Tenant();
        newTenant.setName(tenantDto.getName());
        newTenant.setTenantEmail(tenantDto.getEmail());
        newTenant.setPhone(tenantDto.getPhone());
        newTenant.setRole(tenantDto.getRole());
        
        String hashedPassword = passwordEncoder.encode(tenantDto.getPassword());
        newTenant.setPassword(hashedPassword);
        tenantRepository.save(newTenant);
    }

    @Override
    public Tenant findByTenantEmail(String email) {
       return tenantRepository.findByTenantEmail(email);
    }

    @Override
    public List<TenantDto> findAllTenant() {
        List<Tenant> tenants = tenantRepository.findAll();
        return tenants.stream()
            .map((tenant) -> mapTenantDto(tenant)).collect(Collectors.toList());
    }

    private TenantDto mapTenantDto(Tenant tenant) {
        TenantDto tenantDto = new TenantDto();
        tenantDto.setName(tenant.getName());
        tenantDto.setEmail(tenant.getTenantEmail());
        tenantDto.setPhone(tenant.getPhone());
        return tenantDto;
    }

    @Override
    public void updatePassword(Tenant tenant, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        tenant.setPassword(encodedPassword);

        tenant.setResetPasswordToken(null);
        tenantRepository.save(tenant);
    }

    @Override
    public void updateResetPasswordToken(String token, String email) {
       Tenant tenant = tenantRepository.findByTenantEmail(email);
       if(tenant != null) {
            tenant.setResetPasswordToken(token);
            tenantRepository.save(tenant);
       } else {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản trong hệ thống");
       }
    }

    @Override
    public Tenant findByResetPasswordToken(String token) {
       return tenantRepository.findByResetPasswordToken(token);
    }

    @Override
    public boolean existByEmail(String email) {
        return tenantRepository.findByTenantEmail(email) != null;
    }

    @Override
    public boolean existByResetPasswordToken(String token) {
        return tenantRepository.findByResetPasswordToken(token) != null;
    }
}