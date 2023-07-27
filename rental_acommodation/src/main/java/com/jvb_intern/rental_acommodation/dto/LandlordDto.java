package com.jvb_intern.rental_acommodation.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LandlordDto {
    private Long landlordId;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    @Email(message = "Email không được để trống!")
    private String email;

    @NotNull
    @NotEmpty
    private String phone;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String confirmPassword;
    
    @NotNull
    @NotEmpty
    private String role;
 
}
