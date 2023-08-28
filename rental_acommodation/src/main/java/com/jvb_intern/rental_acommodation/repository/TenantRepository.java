package com.jvb_intern.rental_acommodation.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jvb_intern.rental_acommodation.entity.Tenant;
import com.jvb_intern.rental_acommodation.entity.TenantDating;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
        Tenant findByTenantEmail(String tenantEmail);

        @Query("SELECT u FROM Tenant u WHERE u.resetPasswordToken = ?1")
        Tenant findByResetPasswordToken(String resetPasswordToken);

        @Query("SELECT d FROM Tenant t JOIN t.tenantDatings d WHERE t.tenantEmail =:tenantEmail AND d.postId = :postId")
        TenantDating findDatingExisted(@Param("tenantEmail") String tenantEmail, @Param("postId") Long postId);

        @Query("SELECT d FROM Tenant t JOIN t.tenantDatings d WHERE t.tenantEmail =:tenantEmail AND d.bookingDate = :bookingDate AND d.bookingTime = :bookingTime")
        List<TenantDating> findDatingTimeDuplicate(@Param("tenantEmail") String tenantEmail,
                        @Param("bookingDate") LocalDate bookingDate, @Param("bookingTime") LocalTime bookingTime);

        // Lấy ra danh sách lịch hẹn đang chờ xác nhận từ Landlord
        @Query("SELECT d " +
                        "FROM Tenant t " +
                        "JOIN t.tenantDatings d " +
                        "WHERE t.tenantId = :tenantId " +
                        "AND d.isSending = true " +
                        "AND d.isCanceled = false " +
                        "AND d.confirmStatus = false")
        List<TenantDating> getWaitingDatings(@Param("tenantId") Long tenantId);

        // Lấy ra danh sách lịch hẹn bị huỷ
        @Query("SELECT d " +
                        "FROM Tenant t " +
                        "JOIN t.tenantDatings d " +
                        "WHERE t.tenantId = :tenantId " +
                        "AND d.isCanceled = true " +
                        "AND d.isSending = true")
        List<TenantDating> getCanceledDatings(@Param("tenantId") Long tenantId);

        @Query("SELECT d FROM Tenant t " +
                        "JOIN t.tenantDatings d " +
                        "ON t.tenantId = :tenantId " +
                        "AND d.isSending = true " +
                        "AND d.confirmStatus = true " +
                        "AND d.isCanceled = false " +
                        "AND d.isCanceledByLandlord = false")
        List<TenantDating> getSuccessDating(@Param("tenantId") Long tenantId);

}
