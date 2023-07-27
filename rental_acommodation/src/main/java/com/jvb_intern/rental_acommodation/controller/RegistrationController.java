package com.jvb_intern.rental_acommodation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.jvb_intern.rental_acommodation.dto.LandlordDto;
import com.jvb_intern.rental_acommodation.dto.RegistrationDto;
import com.jvb_intern.rental_acommodation.dto.TenantDto;
import com.jvb_intern.rental_acommodation.entity.Landlord;
import com.jvb_intern.rental_acommodation.entity.Tenant;
import com.jvb_intern.rental_acommodation.service.LandlordService;
import com.jvb_intern.rental_acommodation.service.TenantService;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    @Autowired
    private TenantService tenantService;

    @Autowired
    private LandlordService landlordService;

    public RegistrationController(TenantService tenantService, LandlordService landlordService) {
        this.tenantService = tenantService;
        this.landlordService = landlordService;
    }

    @GetMapping("/register")
    public String showRegistration(Model model) {
        RegistrationDto registrationDto = new RegistrationDto();
        model.addAttribute("registration", registrationDto);
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register/save")
    public String saveRegistration(@Valid @ModelAttribute("registration") RegistrationDto registrationDto, Model model, BindingResult result) {
        // data from dto
        String role = registrationDto.getRole();
        String email = registrationDto.getEmail();
        String password = registrationDto.getPassword();
        String confirmPassword = registrationDto.getConfirmPassword();

        // Tenant existingTenant = tenantService.findByTenantEmail(email);
        // String tenantEmail = existingTenant.getTenantEmail();
        // Landlord existingLandlord = landlordService.findByTenantEmail(email);
        // String landlordEmail = existingLandlord.getLandlordEmail();

        // if(existingTenant != null && existingLandlord != null && tenantEmail.equals(landlordEmail)) {
        //     result.rejectValue("email", null, 
        //                     "Tài khoản đã tồn tại trên hệ thống!");
        // }

        if(role.equals("TENANT")) {
            Tenant existingTenant = tenantService.findByTenantEmail(email);
            if(existingTenant != null && existingTenant.getTenantEmail() != null && !existingTenant.getTenantEmail().isEmpty()) {
                result.rejectValue("email", null, 
                            "Tài khoản đã tồn tại!!");
                return "redirect:/register?existed";
            } else if(!password.equals(confirmPassword)) {
                result.rejectValue("email", null, 
                            "Mật khẩu không khớp nhau");
                return "redirect:/register?fail";
            } else if(result.hasErrors()) {
                model.addAttribute("registration", registrationDto);
                return "register";
            } else {
                TenantDto tenantDto = new TenantDto();
                tenantDto.setName(registrationDto.getName());
                tenantDto.setEmail(registrationDto.getEmail());
                tenantDto.setPhone(registrationDto.getPhone());
                tenantDto.setRole("TENANT");
                tenantDto.setPassword(registrationDto.getPassword());
                
                tenantService.saveTenant(tenantDto);
            }
        } else {
            Landlord existingLandlord = landlordService.findByLandlordEmail(email);
            if(existingLandlord != null && existingLandlord.getLandlordEmail() != null && !existingLandlord.getLandlordEmail().isEmpty()) {
                result.rejectValue("email", null, 
                            "Tài khoản đã tồn tại!!");
                return "redirect:/register?existed";
            } else if(!password.equals(confirmPassword)) {
                result.rejectValue("password", null, 
                            "Mật khẩu không khớp nhau");
                return "redirect:/register?fail";
            } else if(result.hasErrors()) {
                model.addAttribute("registration", registrationDto);
                return "register";
            } else {
                LandlordDto landlordDto = new LandlordDto();
                landlordDto.setName(registrationDto.getName());
                landlordDto.setEmail(registrationDto.getEmail());
                landlordDto.setPhone(registrationDto.getPhone());
                landlordDto.setRole("LANDLORD");
                landlordDto.setPassword(registrationDto.getPassword());
                
                landlordService.saveLandlord(landlordDto);
            }
        }
        return "redirect:/register?success";
    }

}
