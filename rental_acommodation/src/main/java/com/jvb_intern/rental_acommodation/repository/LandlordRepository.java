package com.jvb_intern.rental_acommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jvb_intern.rental_acommodation.entity.Landlord;

@Repository
public interface LandlordRepository extends JpaRepository<Landlord, Long> {
    public Landlord findByLandlordEmail(String email);
    public Landlord findByResetPasswordToken(String resetPasswordToken);
}
