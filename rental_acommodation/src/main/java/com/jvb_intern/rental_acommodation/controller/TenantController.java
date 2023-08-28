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
import com.jvb_intern.rental_acommodation.dto.TenantInfoDto;
import com.jvb_intern.rental_acommodation.entity.Post;
import com.jvb_intern.rental_acommodation.exception.DtoException;
import com.jvb_intern.rental_acommodation.exception.UnauthorizedException;
import com.jvb_intern.rental_acommodation.repository.PostRepository;
import com.jvb_intern.rental_acommodation.dto.DatingDto;
import com.jvb_intern.rental_acommodation.service.LandlordDatingService;
import com.jvb_intern.rental_acommodation.service.LandlordHomeService;
import com.jvb_intern.rental_acommodation.service.TenantDatingService;
import com.jvb_intern.rental_acommodation.service.TenantHomeService;

@Controller
public class TenantController {
    @Autowired
    private TenantHomeService tenantHomeService;

    @Autowired
    private LandlordHomeService landlordHomeService;

    @Autowired
    private TenantDatingService tenantDatingService;

    @Autowired
    private LandlordDatingService landlordDatingService;

    @Autowired
    private PostRepository postRepository;

    private Long currentId;

    @GetMapping("/tenant/tenant-home")
    public String showHomeTenant(Model model,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "6") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {

        Sort sortable = Sort.by("createdAt").descending();

        Pageable pageable = PageRequest.of(page, size, sortable);
        model.addAttribute("listPost", tenantHomeService.listPost(pageable));
        return "2-tenant-home";
    }

    // Chi tiết bài đăng
    @GetMapping("/tenant/tenant-post-detail")
    public String showTenantPostDetail(@RequestParam(name = "postId", required = false) Long postID, Model model) {
        currentId = postID;
        // DTO hiển thị bài đăng
        DisplayPostDto displayPostDto = landlordHomeService.findbyPostId(postID);
        model.addAttribute("displayPostDto", displayPostDto);

        // DTO lấy thông tin lịch hẹn
        DatingDto datingDto = new DatingDto();
        model.addAttribute("datingDto", datingDto);
        return "3-tenant-post-detail";
    }

    @PostMapping("/tenant/tenant-post-detail")
    public String saveDating(@ModelAttribute("datingDto") DatingDto datingDto, Model model)
            throws UnauthorizedException, DtoException {
        // Lưu thông tin của post
        Post post = postRepository.findByPostId(currentId);
        datingDto.setLandlord(post.getLandlord());
        datingDto.setPostId(currentId);

        // Authentication của tenant
        Authentication authTenant = SecurityContextHolder.getContext().getAuthentication();

        // Nếu phòng đã hẹn xem trước đó, cập nhật lại
        if (tenantDatingService.checkExistDating(datingDto, authTenant)) {
            tenantDatingService.updateDating(datingDto, authTenant);
            landlordDatingService.addDating(datingDto, authTenant);
            return "redirect:/tenant/tenant-queue?update";
        } else if (tenantDatingService.checkTimeDuplicate(datingDto, authTenant)) { // trùng thời gian
            return "redirect:/tenant/tenant-queue?notification";
        } else {
            tenantDatingService.updateDating(datingDto, authTenant);
            landlordDatingService.addDating(datingDto, authTenant);
            return "redirect:/tenant/tenant-queue?success";
        }
    }

    @GetMapping("/tenant/tenant-edit-info")
    public String showTenantEditInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Hiển thị email ra màn hình thông quan landlordInforDto
        String email = userDetails.getUsername();
        TenantInfoDto tenantInfoDto =new TenantInfoDto();
        tenantInfoDto.setEmail(email);

        model.addAttribute("tenantInfo", tenantInfoDto);
        return "5-tenant-edit-info";
    }

    @PostMapping("/tenant/tenant-edit-info")
    public String saveChangeInfo(@ModelAttribute("tenantInfo") TenantInfoDto tenantInfoDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        String email = userDetails.getUsername();
        tenantHomeService.saveChangeInfo(email, tenantInfoDto);
        return "redirect:/tenant/tenant-edit-info?success";
    }
}
