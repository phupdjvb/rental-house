package com.jvb_intern.rental_acommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jvb_intern.rental_acommodation.entity.Landlord;

@Repository
public interface LandlordRepository extends JpaRepository<Landlord, Long> {
    Landlord findByLandlordEmail(String email);

    @Query("SELECT u FROM Landlord u WHERE u.resetPasswordToken = ?1")
    Landlord findByResetPasswordToken(String resetPasswordToken);
}
