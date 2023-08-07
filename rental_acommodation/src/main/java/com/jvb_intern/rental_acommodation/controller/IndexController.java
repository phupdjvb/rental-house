package com.jvb_intern.rental_acommodation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/home")
    public String showIndex() {
        return "index";
    }
}
