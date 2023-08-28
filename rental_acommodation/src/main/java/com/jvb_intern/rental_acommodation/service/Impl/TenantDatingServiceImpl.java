package com.jvb_intern.rental_acommodation.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.jvb_intern.rental_acommodation.dto.DatingDto;
import com.jvb_intern.rental_acommodation.entity.Tenant;
import com.jvb_intern.rental_acommodation.entity.TenantDating;
import com.jvb_intern.rental_acommodation.exception.DtoException;
import com.jvb_intern.rental_acommodation.exception.NotFoundDatingException;
import com.jvb_intern.rental_acommodation.exception.UnauthorizedException;
import com.jvb_intern.rental_acommodation.repository.TenantDatingRepository;
import com.jvb_intern.rental_acommodation.repository.TenantRepository;
import com.jvb_intern.rental_acommodation.service.TenantDatingService;

@Service
public class TenantDatingServiceImpl implements TenantDatingService {
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private TenantDatingRepository tenantDatingRepository;

    @Override
    public TenantDating updateDating(DatingDto datingDto, Authentication authTenant) throws UnauthorizedException {
        if (authTenant == null || !authTenant.isAuthenticated()) {
            throw new UnauthorizedException("Có vấn đề trong việc xác thực người dùng hiện tại, hãy kiểm tra lại!!");
        }

        // Thông tin của tenant
        UserDetails userDetails = (UserDetails) authTenant.getPrincipal();
        String email = userDetails.getUsername();
        Long postId = datingDto.getPostId();

        Tenant currentTenant = tenantRepository.findByTenantEmail(email);
        // Tìm lịch hẹn với postId của đối tượng tenant
        TenantDating tenantDating = tenantRepository.findDatingExisted(email, postId);

        if (tenantDating != null) {
            String bookingDate = datingDto.getBookingDate();
            LocalDate bookingDateLocal = LocalDate.parse(bookingDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            String bookingTime = datingDto.getBookingTime();
            LocalTime bookingTimeLocal = LocalTime.parse(bookingTime, DateTimeFormatter.ofPattern("HH:mm"));

            tenantDating.setBookingDate(bookingDateLocal);
            tenantDating.setBookingTime(bookingTimeLocal);
        } else {
            tenantDating = new TenantDating();
            tenantDating.setTenant(currentTenant);
            tenantDating.setLandlord(datingDto.getLandlord());

            String bookingDate = datingDto.getBookingDate();
            LocalDate bookingDateLocal = LocalDate.parse(bookingDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            String bookingTime = datingDto.getBookingTime();
            LocalTime bookingTimeLocal = LocalTime.parse(bookingTime, DateTimeFormatter.ofPattern("HH:mm"));

            tenantDating.setBookingDate(bookingDateLocal);
            tenantDating.setBookingTime(bookingTimeLocal);
            tenantDating.setIsSending(true);
            tenantDating.setConfirmStatus(false);
            tenantDating.setIsCanceled(false);
            tenantDating.setPostId(postId);
        }
        tenantDatingRepository.save(tenantDating);
        return tenantDating;
    }

    // Check tenant đã từng hẹn xem phòng mã có postID chưa
    @Override
    public Boolean checkExistDating(DatingDto datingDto, Authentication authTenant) throws DtoException {
        // Lấy thông tin của tài khoản tenant hiện tại
        UserDetails userDetails = (UserDetails) authTenant.getPrincipal();
        String emailTenant = userDetails.getUsername();
        Long currentPostId = datingDto.getPostId();

        return tenantRepository.findDatingExisted(emailTenant, currentPostId) != null;
    }

    @Override
    public Boolean checkTimeDuplicate(DatingDto datingDto, Authentication authTenant) {
        // Ngày hẹn lấy từ Dto
        String bookingDate = datingDto.getBookingDate();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate bookingDateDto = LocalDate.parse(bookingDate, dateFormatter);

        // Thời gian hẹn lấy từ Dto
        String bookingTime = datingDto.getBookingTime();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime bookingTimeDto = LocalTime.parse(bookingTime, timeFormatter);

        // Truy xuất thông tin của tài khoản tenant hiện tại
        UserDetails tenantDetails = (UserDetails) authTenant.getPrincipal();
        String emailTenant = tenantDetails.getUsername();

        // Nếu tìm thấy thì bị trùng lịch
        return !(tenantRepository.findDatingTimeDuplicate(emailTenant, bookingDateDto, bookingTimeDto).isEmpty());
    }

    // Không thể đặt lịch trước thời gian hiện tại
    @Override
    public Boolean isAfterCurrentDateTime(DatingDto datingDto, LocalDate currentDate, LocalTime currentTime) {
        LocalDateTime dateTimeCurrent = currentDate.atTime(currentTime);

        // Ngày hẹn lấy từ Dto
        String bookingDate = datingDto.getBookingDate();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate bookingDateDto = LocalDate.parse(bookingDate, dateFormatter);

        // Thời gian hẹn lấy từ Dto
        String bookingTime = datingDto.getBookingTime();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime bookingTimeDto = LocalTime.parse(bookingTime, timeFormatter);

        LocalDateTime bookingDateTime = bookingDateDto.atTime(bookingTimeDto);
        return bookingDateTime.isAfter(dateTimeCurrent);
    }

    // Lấy ra lịch hẹn đang chờ landlord xác nhận
    @Override
    public List<TenantDating> getWaitDatings(Authentication authTenant) {
        UserDetails userDetailsTenant = (UserDetails) authTenant.getPrincipal();
        String tenantEmail = userDetailsTenant.getUsername();

        // Lấy ra id của tenant
        Long tenantId = tenantRepository.findByTenantEmail(tenantEmail).getTenantId();
        return tenantRepository.getWaitingDatings(tenantId);
    }

    // Lấy ra lịch hẹn bị huỷ của tenant
    @Override
    public List<TenantDating> getCanceledDatings(Authentication authTenant) {
        UserDetails userDetailsTenant = (UserDetails) authTenant.getPrincipal();
        String tenantEmail = userDetailsTenant.getUsername();

        // Lấy ra id của tenant
        Long tenantId = tenantRepository.findByTenantEmail(tenantEmail).getTenantId();
        return tenantRepository.getCanceledDatings(tenantId);
    }

    @Override
    public TenantDating findByDatingId(Long datingid) {
        return tenantDatingRepository.findByDatingId(datingid);
    }

    // Huỷ lịch hẹn bằng cách lấy tenantDating, gán isCanceled = true
    @Override
    public Boolean cancelDating(Long datingId) throws NotFoundDatingException {
        TenantDating canceledDating = tenantDatingRepository.findByDatingId(datingId);

        if (canceledDating == null) {
            throw new NotFoundDatingException("Không tìm thấy cuộc hẹn!!");
        }

        canceledDating.setIsCanceled(true);
        tenantDatingRepository.save(canceledDating);
        return true;
    }

    // Đặt lại lịch
    @Override
    public TenantDating rebookDating(Long landlordId, Long tenantId, LocalDate bookingDate, LocalTime bookingTime)
            throws NotFoundDatingException {
        // TenantDating existedDating = tenantDatingRepository.findDatingByInfo(landlordId, tenantId, bookingDate, bookingTime);
        // existedDating.setConfirmStatus(true);
        return null;
    }

    @Override
    public TenantDating acceptConfirm(Long landlordId, Long tenantId, LocalDate bookingDate, LocalTime bookingTime) {
        TenantDating existedDating = tenantDatingRepository.findDatingByInfo(landlordId, tenantId, bookingDate, bookingTime);
        existedDating.setConfirmStatus(true);
        tenantDatingRepository.save(existedDating);
        return existedDating;
    }

    // Đánh dấu huỷ bởi landlord
    @Override
    public TenantDating canceledByLandlord(Long landlordId, Long tenantId, LocalDate bookingDate,
            LocalTime bookingTime) {
        TenantDating existedDating = tenantDatingRepository.findDatingByInfo(landlordId, tenantId, bookingDate, bookingTime);
        existedDating.setIsCanceledByLandlord(true);
        tenantDatingRepository.save(existedDating);
        return existedDating;
    }

    // Lấy ra lịch hẹn thành công
    @Override
    public List<TenantDating> getSuccessDating(Authentication authTenant) {
        UserDetails userDetailsLandlord = (UserDetails) authTenant.getPrincipal();

        String emailTenant = userDetailsLandlord.getUsername();
        Long landlordId = tenantRepository.findByTenantEmail(emailTenant).getTenantId();
        return tenantRepository.getSuccessDating(landlordId);
    }

    // Đặt lại lịch của Tenant, set up isCanceled = false
    @Override
    public TenantDating rebook(Long datingId) {
        TenantDating bookedDating = tenantDatingRepository.findByDatingId(datingId);
        bookedDating.setIsCanceled(false);
        return bookedDating;
    }
}
