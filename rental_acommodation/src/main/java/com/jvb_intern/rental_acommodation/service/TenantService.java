package com.jvb_intern.rental_acommodation.service;

import com.jvb_intern.rental_acommodation.dto.RegistrationDto;
import com.jvb_intern.rental_acommodation.entity.Tenant;

public interface TenantService {
    void saveTenant(RegistrationDto registrationDto);

    Tenant findByTenantEmail(String email);

    boolean existByEmail(String email);

    boolean existByResetPasswordToken(String token);

    // for reset password
    void updatePassword(Tenant tenant, String newPassword);

    void updateResetPasswordToken(String token, String email);

    Tenant findByResetPasswordToken(String token);
}
