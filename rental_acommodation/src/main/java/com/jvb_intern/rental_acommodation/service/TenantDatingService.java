package com.jvb_intern.rental_acommodation.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.security.core.Authentication;

import com.jvb_intern.rental_acommodation.dto.DatingDto;
import com.jvb_intern.rental_acommodation.entity.TenantDating;
import com.jvb_intern.rental_acommodation.exception.DtoException;
import com.jvb_intern.rental_acommodation.exception.NotFoundDatingException;
import com.jvb_intern.rental_acommodation.exception.UnauthorizedException;

public interface TenantDatingService {
    TenantDating updateDating(DatingDto datingDto, Authentication authentication) throws UnauthorizedException;
    
    Boolean checkExistDating(DatingDto datingDto, Authentication authentication) throws DtoException;

    Boolean checkTimeDuplicate(DatingDto datingDto, Authentication authentication);

    Boolean isAfterCurrentDateTime(DatingDto datingDto, LocalDate currentDate, LocalTime currentTime);

    // Lấy ra những lịch hẹn đang chờ landlord xác nhận của tenant
    List<TenantDating> getWaitDatings(Authentication authTenant);

    // Lấy ra những lịch hẹn của tenant bị huỷ
    List<TenantDating> getCanceledDatings(Authentication authTenant);

    // Lấy lịch hẹn thông qua Id
    TenantDating findByDatingId(Long datingIdCancel);

    // Tìm datingId tương ứng, đánh dấu isCanceled = true
    Boolean cancelDating(Long datingIdConfirm) throws NotFoundDatingException;

    // Đặt lại lịch thông qua datingId
    TenantDating rebookDating(Long tenantId, Long landlordId, LocalDate bookingDate, LocalTime bookingTime ) throws NotFoundDatingException;

    // Set up trường confirm_status là true khi được người cho thuê xác nhận
    TenantDating acceptConfirm(Long landlordId, Long tenantId, LocalDate bookingDate, LocalTime bookingTime);

    TenantDating canceledByLandlord(Long landlordId, Long tenantId, LocalDate bookingDate, LocalTime bookingTime);

    List<TenantDating> getSuccessDating(Authentication authTenant);

    // Rebook lại lịch có datingId
    TenantDating rebook(Long datingId);
}
