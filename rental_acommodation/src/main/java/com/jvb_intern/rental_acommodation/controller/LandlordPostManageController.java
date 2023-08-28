package com.jvb_intern.rental_acommodation.controller;

import java.io.File;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jvb_intern.rental_acommodation.common.Constant;
import com.jvb_intern.rental_acommodation.dto.ContentPostDto;
import com.jvb_intern.rental_acommodation.dto.DisplayPostDto;
import com.jvb_intern.rental_acommodation.service.FileUploadService;
import com.jvb_intern.rental_acommodation.service.LandlordHomeService;
import com.jvb_intern.rental_acommodation.service.PostService;

@Controller
public class LandlordPostManageController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private LandlordHomeService landlordHomeService;

    @GetMapping("/landlord/landlord-manager-post/{postId}")
    public String showFormManager(@PathVariable("postId") Long postId,
            Model model) {

        model.addAttribute("postIdFromUrl", postId);
        DisplayPostDto displayPostDto = landlordHomeService.findbyPostId(postId);
        model.addAttribute("post", displayPostDto);

        ContentPostDto updatePost = postService.getPostById(postId);
        model.addAttribute("updatePost", updatePost);
        return "15-landlord-manager-post";
    }

    @PostMapping("/landlord/landlord-manager-post")
    public String modifierPost(@RequestParam String action,
            @RequestParam("postId") Long postId,
            @ModelAttribute("updatePost") ContentPostDto updatePostDto,
            Principal principal, RedirectAttributes redirectAttributes) {

        Authentication authLandlord = SecurityContextHolder.getContext().getAuthentication();

        // Xoá và ngừng cho thuê
        if (action.equals("delete")) {
            postService.detelePost(postId); // xoá bài đăng
            redirectAttributes.addAttribute("deleted", true);
            return "redirect:/landlord/landlord-manager-post/" + postId;
        }

        // đăng lại bài đăng
        if (action.equals("repost")) {
            postService.repost(postId); // đăng lại bài đăng
            redirectAttributes.addAttribute("reposted", true);
            return "redirect:/landlord/landlord-manager-post/" + postId;
        }

        // Cập nhật bài đăng
        if (action.equals("update")) {
            // Cập nhật bài đăng
            MultipartFile uploadedFile = updatePostDto.getPhotoFile();
            if (!uploadedFile.isEmpty()) {
                try {
                    String uploadDir = Constant.BASE_DIRECTOR
                            + "/rental_acommodation/src/main/resources/static/img/uploaded_images/";
                    File directory = new File(uploadDir);

                    // Nếu chưa tồn tại
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    // Lưu file
                    String fileName = StringUtils.cleanPath(uploadedFile.getOriginalFilename());
                    fileUploadService.saveFile(uploadDir, fileName, uploadedFile);

                    // Lấy url
                    updatePostDto.setPhoto(StringUtils.cleanPath("/img/uploaded_images/" + fileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            postService.updatePost(postId, updatePostDto, authLandlord);
            redirectAttributes.addAttribute("updateSuccess", true);
            return "redirect:/landlord/landlord-manager-post/" + postId;
        }

        return "redirect:/landlord/landlord-manager-post/" + postId;
    }

    @GetMapping("/landlord/landlord-deleted-post")
    public String showDeletedPost(Model model,
                                @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                @RequestParam(name = "size", required = false, defaultValue = "6") Integer size) {

        // Danh sách bài đăng đã xoá
        Authentication authLandlord = SecurityContextHolder.getContext().getAuthentication();
        Pageable pageable = PageRequest.of(page, size);// tạo đối tượng pageble
        model.addAttribute("listDeletedPost", postService.getDeletedPost(authLandlord, pageable));
                                    
        return "16-landlord-deleted-post";
    }
}
