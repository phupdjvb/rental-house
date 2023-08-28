package com.jvb_intern.rental_acommodation.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * Objective: Get data from screen the create post of landlord, 
 * Relation: 10-landlord-post.html
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContentPostDto {
    private String title;
    
    private String content;

    private MultipartFile photoFile;

    private String photo;

    private Double roomPrice;

    private String area;

    private String address;

    private Double square;

    private Boolean roomStatus;

    private String priceCategory;

    private String parking;

    private String wifi;

    private String conditioner;

    private String tag;
}
