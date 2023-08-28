package com.jvb_intern.rental_acommodation.service;

import com.jvb_intern.rental_acommodation.dto.TenantInfoDto;

public interface TenantHomeService extends HomeService{
    void saveChangeInfo(String email, TenantInfoDto tenantInfoDto);
}
