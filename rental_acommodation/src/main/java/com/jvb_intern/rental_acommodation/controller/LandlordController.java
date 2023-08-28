package com.jvb_intern.rental_acommodation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jvb_intern.rental_acommodation.dto.DisplayPostDto;
import com.jvb_intern.rental_acommodation.dto.LandlordInfoDto;
import com.jvb_intern.rental_acommodation.service.LandlordHomeService;

@Controller
public class LandlordController {
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

    @GetMapping("/landlord/landlord-posted")
    public String showLandlordPost(Model model,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "6") Integer size) {

        // Lấy email người dùng
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Sort sortable = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, sortable);
        model.addAttribute("listPosted", landlordHomeService.searchPostByEmail(email, pageable));
        return "11-landlord-posted";
    }

    // Chỉnh sủa thông tin cá nhân 
    @GetMapping("/landlord/landlord-edit-info")
    public String showLandlordEditInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Hiển thị email ra màn hình thông quan landlordInforDto
        String email = userDetails.getUsername();
        LandlordInfoDto landlordInfoDto = new LandlordInfoDto(); 
        landlordInfoDto.setEmail(email);

        model.addAttribute("landlordInfo", landlordInfoDto);
        return "9-landlord-edit-info";
    }

    @PostMapping("/landlord/landlord-edit-info")
    public String saveChangeInfo(@ModelAttribute("landlordInfo") LandlordInfoDto landlordInfoDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        String email = userDetails.getUsername();
        landlordHomeService.saveChangeInfo(email, landlordInfoDto);
        return "redirect:/landlord/landlord-edit-info?success";
    }

    // Chi tiết bài đăng
    @GetMapping("/landlord/landlord-post-detail")
    public String showLandlordPostDetail(@RequestParam(name = "postId", required = false) Long postID, Model model) {
        DisplayPostDto displayPostDto = landlordHomeService.findbyPostId(postID);
        model.addAttribute("displayPostDto", displayPostDto);
        return "12-landlord-post-detail";
    }
}