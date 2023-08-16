package com.jvb_intern.rental_acommodation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jvb_intern.rental_acommodation.common.Constant;
import com.jvb_intern.rental_acommodation.dto.RegistrationDto;
import com.jvb_intern.rental_acommodation.entity.Landlord;
import com.jvb_intern.rental_acommodation.repository.LandlordRepository;
import com.jvb_intern.rental_acommodation.service.LandlordService;

@Service
public class LandlordServiceImpl implements LandlordService {
    @Autowired
    private LandlordRepository landlordReposiry;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LandlordServiceImpl(LandlordRepository landlordReposiry, PasswordEncoder passwordEncoder) {
        this.landlordReposiry = landlordReposiry;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveLandlord(RegistrationDto registrationDto) {
        if (!registrationDto.getRole().equals(Constant.ROLE_LANDLORD)) {
            return;
        }

        Landlord newLandlord = new Landlord();

        newLandlord.setName(registrationDto.getName());
        newLandlord.setLandlordEmail(registrationDto.getEmail());
        newLandlord.setPhone(registrationDto.getPhone());
        newLandlord.setRole(Constant.ROLE_LANDLORD);

        // mã hoá mật khẩu
        String hashedPassword = passwordEncoder.encode(registrationDto.getPassword());
        newLandlord.setPassword(hashedPassword);
        landlordReposiry.save(newLandlord);
    }

    /* Tìm mail */
    @Override
    public Landlord findByLandlordEmail(String email) {
        return landlordReposiry.findByLandlordEmail(email);
    }

    /* Cập nhật mật khẩu */
    @Override
    public void updatePassword(Landlord landlord, String newPassword) {
        String encodePassword = passwordEncoder.encode(newPassword);
        landlord.setPassword(encodePassword);

        landlord.setResetPasswordToken(null);
        landlordReposiry.save(landlord);

    }

    /* Cập nhật token khi update password */
    @Override
    public void updateResetPasswordToken(String token, String email) {
        Landlord landlord = landlordReposiry.findByLandlordEmail(email);
        if (landlord != null) {
            landlord.setResetPasswordToken(token);
            landlordReposiry.save(landlord);
        } else {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản trong hệ thống");
        }

    }

    /* Tìm thông qua token */
    @Override
    public Landlord findByResetPasswordToken(String token) {
        return landlordReposiry.findByResetPasswordToken(token);
    }

    /* Check mail tồn tại */
    @Override
    public boolean existByEmail(String email) {
        return landlordReposiry.findByLandlordEmail(email) != null;
    }

    /* Check token tồn tại */
    @Override
    public boolean existByResetPasswordToken(String token) {
        return landlordReposiry.findByResetPasswordToken(token) != null;
    }
}
