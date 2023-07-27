package com.jvb_intern.rental_acommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jvb_intern.rental_acommodation.entity.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    public Tenant findByTenantEmail(String email);
    public Tenant findByResetPasswordToken(String resetPasswordToken);
}
