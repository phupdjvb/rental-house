package com.jvb_intern.rental_acommodation.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jvb_intern.rental_acommodation.entity.TenantDating;

@Repository
public interface TenantDatingRepository extends JpaRepository<TenantDating, Long> {
    @Query("SELECT d FROM TenantDating d WHERE d.postId = ?1")
    TenantDating findByPostId(Long postId);

    // Tim tenantDating thông qua id
    TenantDating findByDatingId(Long datingId);

    // Tìm ra lịch hẹn
    @Query("SELECT d FROM TenantDating d WHERE d.landlord.landlordId = ?1 AND d.tenant.tenantId = ?2 AND d.bookingDate = ?3 AND d.bookingTime = ?4")
    TenantDating findDatingByInfo(Long landlordId, Long tenantId, LocalDate bookingDate, LocalTime bookingTime);
}
