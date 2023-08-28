package com.jvb_intern.rental_acommodation.dto;

import com.jvb_intern.rental_acommodation.entity.Landlord;

import lombok.Data;

@Data
public class DisplayPostDto {
    private String email;

    private String phone;

    private String name;
    
    private String title;
    
    private String content;

    private String photo;

    private Double roomPrice;

    private String area;

    private String address;

    private Double square;

    private String priceCategory;

    private String parking;

    private String wifi;

    private String conditioner;

    private Landlord landlord;

    private Boolean roomStatus;

    private Boolean isDeleted;

    private Long postId;

}
