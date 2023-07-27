package com.jvb_intern.rental_acommodation.service;

import java.util.List;

import com.jvb_intern.rental_acommodation.dto.TenantDto;
import com.jvb_intern.rental_acommodation.entity.Tenant;

public interface TenantService {
    void saveTenant(TenantDto tenantDto);
    Tenant findByTenantEmail(String email);
    List<TenantDto> findAllTenant();
    boolean existByEmail(String email);
    boolean existByResetPasswordToken(String token);


    // for reset password
    void updatePassword(Tenant tenant, String newPassword);
    void updateResetPasswordToken(String token, String email);
    Tenant findByResetPasswordToken(String token);
}
