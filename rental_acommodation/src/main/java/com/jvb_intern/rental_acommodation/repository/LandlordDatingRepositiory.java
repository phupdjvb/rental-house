package com.jvb_intern.rental_acommodation.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jvb_intern.rental_acommodation.entity.LandlordDating;

@Repository
public interface LandlordDatingRepositiory extends JpaRepository<LandlordDating, Long> {
    // Tìm lịch hẹn thông qua id
    LandlordDating findByDatingId(Long datingId);

    @Query("SELECT d FROM LandlordDating d WHERE d.landlord.landlordId = ?1 AND d.tenant.tenantId = ?2 AND d.bookingDate = ?3 AND d.bookingTime = ?4")
    LandlordDating findDatingByTenant(Long landlordId, Long tenantId, LocalDate bookingDate, LocalTime bookingTime);

    // Lấy ra các lịch hẹn chờ xác nhận
    @Query("SELECT DISTINCT d FROM LandlordDating d WHERE d.landlord.landlordId = ?1 AND d.isReceived = true AND d.confirmStatus = false AND d.isCanceledByLandlord = false AND d.isCanceledByTenant = false")
    List<LandlordDating> getWaitingDatings(Long landlordId);

    // 
}
