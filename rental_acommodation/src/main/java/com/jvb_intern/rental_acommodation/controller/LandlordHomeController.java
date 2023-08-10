package com.jvb_intern.rental_acommodation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jvb_intern.rental_acommodation.service.LandlordHomeService;

@Controller
public class LandlordHomeController {
    @Autowired
    private LandlordHomeService landlordHomeService;

    // GET, MH: 8-landlord-home.html, sắp xếp theo thứ tự giảm dần
    @GetMapping("/landlord/landlord-home")
    public String showHomeLandlord(Model model,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "6") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {

        Sort sortable = Sort.by("createdAt").descending();

        Pageable pageable = PageRequest.of(page, size, sortable);
        model.addAttribute("listPost", landlordHomeService.listPost(pageable));
        return "8-landlord-home";
    }
}