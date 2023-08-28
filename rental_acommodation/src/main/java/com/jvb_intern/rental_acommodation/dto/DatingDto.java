package com.jvb_intern.rental_acommodation.dto;

import org.springframework.format.annotation.DateTimeFormat;

import com.jvb_intern.rental_acommodation.entity.Landlord;
import com.jvb_intern.rental_acommodation.entity.Tenant;

import lombok.Data;

@Data
public class DatingDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String bookingDate;

    private String bookingTime;
    
    // thông tin người cho thuê
    private Landlord landlord;

    // thông tin người tìm trọ
    private Tenant tenant;

    private Long postId;
}
