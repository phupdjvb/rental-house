package com.jvb_intern.rental_acommodation.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.jvb_intern.rental_acommodation.dto.DatingDto;
import com.jvb_intern.rental_acommodation.entity.LandlordDating;
import com.jvb_intern.rental_acommodation.entity.Tenant;
import com.jvb_intern.rental_acommodation.entity.TenantDating;
import com.jvb_intern.rental_acommodation.exception.UnauthorizedException;
import com.jvb_intern.rental_acommodation.repository.LandlordDatingRepositiory;
import com.jvb_intern.rental_acommodation.repository.LandlordRepository;
import com.jvb_intern.rental_acommodation.repository.TenantRepository;
import com.jvb_intern.rental_acommodation.service.LandlordDatingService;

@Service
public class LandlordDatingServiceImpl implements LandlordDatingService {
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private LandlordDatingRepositiory landlordDatingRepositiory;

    @Autowired
    private LandlordRepository landlordRepository;

    @Override
    public LandlordDating addDating(DatingDto datingDto, Authentication authTenant) throws UnauthorizedException {
        if(authTenant == null || !authTenant.isAuthenticated()) {
            throw new UnauthorizedException("Không thể xác thực");
        }

        UserDetails userDetails = (UserDetails) authTenant.getPrincipal();
        String email = userDetails.getUsername();

        Tenant currentTenant = tenantRepository.findByTenantEmail(email);

        LandlordDating landlordDating = new LandlordDating();
        landlordDating.setTenant(currentTenant);
        landlordDating.setLandlord(datingDto.getLandlord());

        // ngày hẹn phòng
        String bookingDate = datingDto.getBookingDate();
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(bookingDate, formatDate);
        landlordDating.setBookingDate(date);

        // Giờ xem phòng
        String bookingTime = datingDto.getBookingTime();
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm"); 
        LocalTime time = LocalTime.parse(bookingTime, formatterTime);
        landlordDating.setBookingTime(time);   
        
        // Đánh dấu là đã nhận thông báo
        landlordDating.setIsReceived(true);

        // Đánh dâu là chưa xác nhận hẹn
        landlordDating.setConfirmStatus(false);

        // Đánh dấu là chưa huỷ
        landlordDating.setIsCanceledByTenant(false);
        landlordDating.setIsCanceledByLandlord(false);
        landlordDating.setPostId(datingDto.getPostId());
        landlordDatingRepositiory.save(landlordDating);
        return landlordDating;
    }

    // Khi landlord nhấn huỷ lịch, tìm lịch tương ứng và set up isCanceledByTenant = true
    @Override
    public LandlordDating cancelByTenant(Long tenantId, Long landlordId, LocalDate bookingDate,
            LocalTime bookingTime) {
        LandlordDating cancelLandlordDating = landlordDatingRepositiory.findDatingByTenant(landlordId, tenantId, bookingDate, bookingTime);
        cancelLandlordDating.setIsCanceledByTenant(true);
        landlordDatingRepositiory.save(cancelLandlordDating);
        return cancelLandlordDating;
    }

    @Override
    public List<LandlordDating> getWaitingDatings(Authentication authLandlord) {
        UserDetails userDetailsLandlord = (UserDetails) authLandlord.getPrincipal();

        String landlordEmail = userDetailsLandlord.getUsername();

        Long landlordId = landlordRepository.findByLandlordEmail(landlordEmail).getLandlordId();
        return landlordDatingRepositiory.getWaitingDatings(landlordId);
    }

    // Tìm bài đăng theo Id và xoá
    @Override
    public LandlordDating cancelDating(Long datingIdCancel) {
        LandlordDating existedLandlordDating = landlordDatingRepositiory.findByDatingId(datingIdCancel);
        existedLandlordDating.setIsCanceledByLandlord(true);
        landlordDatingRepositiory.save(existedLandlordDating);
        return existedLandlordDating;
    }

    @Override
    public LandlordDating confirmDating(Long datingIdConfirm) {
        LandlordDating existedLandlordDating = landlordDatingRepositiory.findByDatingId(datingIdConfirm);
        existedLandlordDating.setConfirmStatus(true);
        landlordDatingRepositiory.save(existedLandlordDating);
        return existedLandlordDating;
    }

    @Override
    public List<LandlordDating> getCanceledDatingByLandlord(Authentication authLandlord) {
        UserDetails userDetailsLandlord = (UserDetails) authLandlord.getPrincipal();

        String email = userDetailsLandlord.getUsername();
    
        Long landlordId = landlordRepository.findByLandlordEmail(email).getLandlordId();
        return landlordRepository.getCanceledDatingsByLandlord(landlordId);
    }

    @Override
    public List<LandlordDating> getCanceledDatingByTenant(Authentication authLandlord) {
        UserDetails userDetailsLandlord = (UserDetails) authLandlord.getPrincipal();

        String email = userDetailsLandlord.getUsername();
        Long landlordId = landlordRepository.findByLandlordEmail(email).getLandlordId();
        return landlordRepository.getCanceledDatingsByTenant(landlordId);
    }

    @Override
    public List<LandlordDating> getSuccessDating(Authentication authLandlord) {
        UserDetails userDetailsLandlord = (UserDetails) authLandlord.getPrincipal();
        String email = userDetailsLandlord.getUsername();
        Long landlordId = landlordRepository.findByLandlordEmail(email).getLandlordId();
        return landlordRepository.getSuccessDating(landlordId);
    }

    // Thêm lịch mới vào bảng LandlordDating thông quan tenantDating
    @Override
    public LandlordDating saveTenantDating(TenantDating tenantDating) {
        LandlordDating landlordDating = new LandlordDating();

        landlordDating.setBookingTime(tenantDating.getBookingTime());
        landlordDating.setBookingDate(tenantDating.getBookingDate());
        landlordDating.setLandlord(tenantDating.getLandlord());
        landlordDating.setTenant(tenantDating.getTenant());
        landlordDating.setPostId(tenantDating.getPostId());

        landlordDating.setConfirmStatus(false);
        landlordDating.setIsReceived(true);
        landlordDating.setIsCanceledByLandlord(false);
        landlordDating.setIsCanceledByTenant(false);
        landlordDatingRepositiory.save(landlordDating);
        return landlordDating;
    }
}
