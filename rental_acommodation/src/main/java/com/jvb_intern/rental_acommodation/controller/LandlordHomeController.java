package com.jvb_intern.rental_acommodation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandlordHomeController {
    // Get, MH: 8-landlord-home
    @GetMapping("/landlord/landlord-home")
    public String showLandlordListPost() {
        return "8-landlord-home";
    }  
}
