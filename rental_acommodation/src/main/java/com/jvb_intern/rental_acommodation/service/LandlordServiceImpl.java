package com.jvb_intern.rental_acommodation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jvb_intern.rental_acommodation.dto.LandlordDto;
import com.jvb_intern.rental_acommodation.entity.Landlord;
import com.jvb_intern.rental_acommodation.repository.LandlordRepository;

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
    public void saveLandlord(LandlordDto landlordDto) {
        Landlord newLandlord = new Landlord();
        newLandlord.setName(landlordDto.getName());
        newLandlord.setLandlordEmail(landlordDto.getEmail());
        newLandlord.setPhone(landlordDto.getPhone());
        newLandlord.setRole(landlordDto.getRole());

        String hashedPassword = passwordEncoder.encode(landlordDto.getPassword());
        newLandlord.setPassword(hashedPassword);
        landlordReposiry.save(newLandlord);
    }

    @Override
    public Landlord findByLandlordEmail(String email) {
        return landlordReposiry.findByLandlordEmail(email);
    }

    @Override
    public List<LandlordDto> findAllLandlord() {
        List<Landlord> landlords = landlordReposiry.findAll();
        return (landlords.stream())
                .map((landlord) -> mapLandlordDto(landlord)).collect(Collectors.toList());
    }

    // convert entity to dto
    private LandlordDto mapLandlordDto(Landlord tenant) {
        LandlordDto landlordDto = new LandlordDto();
        landlordDto.setName(tenant.getName());
        landlordDto.setEmail(tenant.getLandlordEmail());
        landlordDto.setPhone(tenant.getPhone());
        return landlordDto;
    }

    @Override
    public void updatePassword(Landlord landlord, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(newPassword);
        landlord.setPassword(encodePassword);

        landlord.setResetPasswordToken(null);
        landlordReposiry.save(landlord);

    }

    @Override
    public void updateResetPasswordToken(String token, String email) {
        Landlord landlord = landlordReposiry.findByLandlordEmail(email);
        if(landlord != null) {
            landlord.setResetPasswordToken(token);
            landlordReposiry.save(landlord);
        } else {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản trong hệ thống");
        }
      
    }

    @Override
    public Landlord findByResetPasswordToken(String token) {
       return landlordReposiry.findByResetPasswordToken(token);
    }

    @Override
    public boolean existByEmail(String email) {
        return landlordReposiry.findByLandlordEmail(email) != null;
    }

    @Override
    public boolean existByResetPasswordToken(String token) {
        return landlordReposiry.findByResetPasswordToken(token) != null;
    }
}
