package com.jvb_intern.rental_acommodation.service;

import com.jvb_intern.rental_acommodation.dto.RegistrationDto;

public interface RegistrationService {
    Boolean checkExistedAccount(RegistrationDto registrationDto);

    Boolean checkExistedRole(RegistrationDto registrationDto, String role);

    Boolean checkValidPassword(RegistrationDto registrationDto);
}
