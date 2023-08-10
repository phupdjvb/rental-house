package com.jvb_intern.rental_acommodation.service;

import com.jvb_intern.rental_acommodation.dto.RegistrationDto;
import com.jvb_intern.rental_acommodation.entity.Landlord;

public interface LandlordService {
    void saveLandlord(RegistrationDto registrationDto);

    Landlord findByLandlordEmail(String email);

    boolean existByEmail(String email);

    boolean existByResetPasswordToken(String token);

    // for reset password
    void updatePassword(Landlord landlord, String newPassword);

    void updateResetPasswordToken(String token, String email);

    Landlord findByResetPasswordToken(String token);
}
