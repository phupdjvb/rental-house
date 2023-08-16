package com.jvb_intern.rental_acommodation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jvb_intern.rental_acommodation.common.Constant;
import com.jvb_intern.rental_acommodation.dto.RegistrationDto;
import com.jvb_intern.rental_acommodation.entity.Landlord;
import com.jvb_intern.rental_acommodation.entity.Tenant;
import com.jvb_intern.rental_acommodation.repository.LandlordRepository;
import com.jvb_intern.rental_acommodation.repository.TenantRepository;
import com.jvb_intern.rental_acommodation.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private LandlordRepository landlordRepository;

    /* Một email không thể đăng ký với 2 vai trò Tenant và Landlord */
    @Override
    public Boolean checkExistedAccount(RegistrationDto registrationDto) {
        String inputEmail = registrationDto.getEmail();
        String inputRole = registrationDto.getRole();

        /*
         * Nếu đăng ký với vai trò tenant thì email không được tồn tại trên bảng
         * Landlord
         */
        if (inputRole.equals(Constant.ROLE_TENANT)) {
            Landlord landlord = landlordRepository.findByLandlordEmail(inputEmail);
            return (landlord != null && landlord.getLandlordEmail().equals(inputEmail));
        }

        /*
         * Nếu đăng ký với vai trò landlord thì email không được tồn tại trên bảng
         * Tenant
         */
        Tenant tenant = tenantRepository.findByTenantEmail(inputEmail);
        return (tenant != null && tenant.getTenantEmail().equals(inputEmail));

    }

    /* Check đã tồn tại với vai trò role hay chưa  */
    @Override
    public Boolean checkExistedRole(RegistrationDto registrationDto, String role) {
        String inputEmail = registrationDto.getEmail();

        // nếu đăng ký vai trò tenat
        if  (role.equals(Constant.ROLE_TENANT)) {
            Tenant tenant = tenantRepository.findByTenantEmail(inputEmail);
            return (tenant != null);
        }
        Landlord landlord = landlordRepository.findByLandlordEmail(inputEmail);
        return (landlord != null);
    }

    /* Check mật khẩu xác nhận có khớp không */
    @Override
    public Boolean checkValidPassword(RegistrationDto registrationDto) {
        String password = registrationDto.getPassword();
        String confirmPassword = registrationDto.getConfirmPassword();
        return (password.equals(confirmPassword));
    }
}
