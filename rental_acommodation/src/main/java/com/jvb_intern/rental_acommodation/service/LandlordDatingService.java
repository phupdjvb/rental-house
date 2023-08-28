package com.jvb_intern.rental_acommodation.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.security.core.Authentication;

import com.jvb_intern.rental_acommodation.dto.DatingDto;
import com.jvb_intern.rental_acommodation.entity.LandlordDating;
import com.jvb_intern.rental_acommodation.entity.TenantDating;
import com.jvb_intern.rental_acommodation.exception.UnauthorizedException;

public interface LandlordDatingService {
    LandlordDating addDating(DatingDto datingDto, Authentication authTenant) throws UnauthorizedException;

    LandlordDating cancelByTenant(Long tenantId, Long landlordId, LocalDate bookingDate, LocalTime bookingTime);

    // Lấy danh sách lịch hẹn chờ xác nhận dành cho landlord
    List<LandlordDating> getWaitingDatings(Authentication authLandlord);

    // huỷ lịch hẹn ứng với datingId tương ứng
    LandlordDating cancelDating(Long datingId);

    // Xác nhận lịch hẹn
    LandlordDating confirmDating(Long datingId);

    // Lấy ra lịch hẹn đã huỷ bởi Landlord
    List<LandlordDating> getCanceledDatingByLandlord(Authentication authLandlord);

    // Lấy ra lịch hẹn đã huỷ bởi Người tìm trọ
    List<LandlordDating> getCanceledDatingByTenant(Authentication authLandlord);

    // Lấy ra lịch hẹn thành công
    List<LandlordDating> getSuccessDating(Authentication authLandlord);

    LandlordDating saveTenantDating(TenantDating bookedDating);
}
