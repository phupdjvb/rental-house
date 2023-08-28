package com.jvb_intern.rental_acommodation.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jvb_intern.rental_acommodation.entity.Landlord;
import com.jvb_intern.rental_acommodation.entity.LandlordDating;
import com.jvb_intern.rental_acommodation.entity.Post;

@Repository
public interface LandlordRepository extends JpaRepository<Landlord, Long> {
        Landlord findByLandlordEmail(String email);

        Landlord findByLandlordId(Long landlordId);

        @Query("SELECT u FROM Landlord u WHERE u.resetPasswordToken = ?1")
        Landlord findByResetPasswordToken(String resetPasswordToken);

        // Danh sách lịch hẹn bị huỷ
        @Query("SELECT d FROM Landlord l JOIN l.landlordDatings d WHERE l.landlordId = ?1 AND d.isCanceledByTenant = true AND d.isReceived = true")
        List<LandlordDating> getCanceledDatingsByTenant(Long landlordId);

        // Danh sách lịch hẹn đã huỷ
        @Query("SELECT d FROM Landlord l JOIN l.landlordDatings d WHERE l.landlordId = ?1 AND d.isCanceledByLandlord = true AND d.isReceived = true")
        List<LandlordDating> getCanceledDatingsByLandlord(Long landlordId);

        // Danh sách lịch hẹn thành công
        @Query("SELECT d FROM Landlord l " +
                        "JOIN l.landlordDatings d " +
                        "WHERE l.landlordId = ?1 " +
                        "AND d.isReceived = true " +
                        "AND d.confirmStatus = true " +
                        "AND d.isCanceledByTenant = false " +
                        "AND d.isCanceledByLandlord = false")
        List<LandlordDating> getSuccessDating(Long landlordId);

        // Lấy ra các bài viêt đã đăng
        @Query("SELECT p FROM Landlord l JOIN l.posts p WHERE l.landlordId = ?1")
        Page<Post> getPosted(Long landlordId, Pageable pageable);

        // Lấy ra các bài đăng đã xoá
        @Query("SELECT p FROM Landlord l JOIN l.posts p WHERE l.landlordId = ?1 AND p.isDeleted = true")
        Page<Post> getdeletedPost(Long landlordId, Pageable pageable);
}
