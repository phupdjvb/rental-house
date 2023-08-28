package com.jvb_intern.rental_acommodation.dto;

import lombok.Data;

@Data
public class TenantInfoDto {
    private String name;

    private String phone;

    private String password;

    private String confirmPassword;

    private String email;
}
