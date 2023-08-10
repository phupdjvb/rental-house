package com.jvb_intern.rental_acommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jvb_intern.rental_acommodation.entity.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Tenant findByTenantEmail(String tenantEmail);

    @Query("SELECT u FROM Tenant u WHERE u.resetPasswordToken = ?1")
    Tenant findByResetPasswordToken(String resetPasswordToken);
}
