package com.jvb_intern.rental_acommodation.service;

import java.util.List;

import com.jvb_intern.rental_acommodation.dto.LandlordDto;
import com.jvb_intern.rental_acommodation.entity.Landlord;

public interface LandlordService {
    void saveLandlord(LandlordDto landlordDto);
    Landlord findByLandlordEmail(String email);
    List<LandlordDto> findAllLandlord();
    boolean existByEmail(String email);
    boolean existByResetPasswordToken(String token);

    // for reset password
    void updatePassword(Landlord landlord, String newPassword);
    void updateResetPasswordToken(String token, String email);
    Landlord findByResetPasswordToken(String token);
}
