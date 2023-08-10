package com.jvb_intern.rental_acommodation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.jvb_intern.rental_acommodation.common.Constant;
import com.jvb_intern.rental_acommodation.dto.RegistrationDto;
import com.jvb_intern.rental_acommodation.service.LandlordService;
import com.jvb_intern.rental_acommodation.service.RegistrationService;
import com.jvb_intern.rental_acommodation.service.TenantService;

import javax.validation.Valid;
@Controller
public class RegistrationController {
    @Autowired
    private TenantService tenantService;

    @Autowired
    private LandlordService landlordService;

    @Autowired
    private RegistrationService registrationService;

    public RegistrationController(TenantService tenantService, LandlordService landlordService) {
        this.tenantService = tenantService;
        this.landlordService = landlordService;
    }

    /* MH: register.html */
    @GetMapping("/register")
    public String showRegistration(Model model) {
        RegistrationDto registrationDto = new RegistrationDto();
        model.addAttribute("registration", registrationDto);
        return "register";
    }

    /* MH: register.html */
    @PostMapping("/register/save")
    public String saveRegistration(@Valid @ModelAttribute("registration") RegistrationDto registrationDto, Model model,
            BindingResult result) {
        String role = registrationDto.getRole();

        /* Nếu email đã tồn tại trên bảng còn lại */
        if(registrationService.checkExistedAccount(registrationDto)) {
            result.rejectValue(Constant.EMAIL, "existed", "Email này đã được đăng ký với vai trò khác!! Vui lòng đăng ký email khác");
            return "redirect:/register?existed";
        }

        /* Nếu tenant tồn tại */
        if(registrationService.checkExistedRole(registrationDto, Constant.ROLE_TENANT)) {
            result.rejectValue(Constant.EMAIL, "existedRole", "Tài khoản đã tồn tại");
            return "redirect:/register?existedRole";
        }

        /* Nếu landlord tồn tại */
        if(registrationService.checkExistedRole(registrationDto, Constant.ROLE_LANDLORD)) {
            result.rejectValue(Constant.EMAIL, "existedRole", "Tài khoản đã tồn tại");
            return "redirect:/register?existedRole";
        }
        
        /* Nếu mật khẩu không khớp nhau */
        if(! registrationService.checkValidPassword(registrationDto)) {
            result.rejectValue("password", null,
                        "Mật khẩu không khớp nhau");
            return "redirect:/register?fail";
        }

        /* Nếu có lỗi */
        if(result.hasErrors()) {
            model.addAttribute("registration", registrationDto);
            return "register";
        }

        // Đăng ký thành công
        if(role.equals(Constant.ROLE_TENANT)) {
            tenantService.saveTenant(registrationDto);
            return "redirect:/register?success";
        }

        landlordService.saveLandlord(registrationDto);
        return "redirect:/register?success";
    }

    /* MH: login.html */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /* MH: rules.html */
    @GetMapping("/rules")
    public String showRules() {
        return "rules";
    }
}
