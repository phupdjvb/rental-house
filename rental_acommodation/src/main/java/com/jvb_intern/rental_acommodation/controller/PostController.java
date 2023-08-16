package com.jvb_intern.rental_acommodation.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.jvb_intern.rental_acommodation.common.Constant;
import com.jvb_intern.rental_acommodation.dto.ContentPostDto;
import com.jvb_intern.rental_acommodation.service.ContentPostService;
import com.jvb_intern.rental_acommodation.service.FileUploadService;

@Controller
public class PostController {
    @Autowired
    private ContentPostService contentPostService;

    @Autowired
    private FileUploadService fileUploadService;

    //MH: 10-landlord-post.html
    @GetMapping("/landlord/landlord-post")
    public String showPostForm(Model model) {
        ContentPostDto newPostDto = new ContentPostDto();
        model.addAttribute("newPostDto", newPostDto);
        return "10-landlord-post";
    }

    // MH: 10-landlord-post.html
    @PostMapping("/landlord/landlord-post")
    public String savePost(@ModelAttribute("newPostDto") ContentPostDto newPostDto, Principal principal) throws IOException {
        MultipartFile uploadedFile = newPostDto.getPhotoFile();

        // Xử lý tên file, thư mục lưu ảnh
        if(!uploadedFile.isEmpty()) {
            try {
                String uploadDir = Constant.BASE_DIRECTOR + "/rental_acommodation/src/main/resources/static/img/uploaded_images/";
                File directory = new File(uploadDir);

                // Nếu chưa tồn tại
                if(!directory.exists()) {
                    directory.mkdirs();
                }

                // Lưu file
                String fileName = StringUtils.cleanPath(uploadedFile.getOriginalFilename());
                fileUploadService.saveFile(uploadDir, fileName, uploadedFile);

                // Lấy url
                newPostDto.setPhoto(StringUtils.cleanPath("/img/uploaded_images/" + fileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // Lưu dữ liệu vào database
        String landlordEmail = principal.getName();
        contentPostService.savePostAndAccommodate(newPostDto, landlordEmail);
        return "redirect:/landlord/landlord-post?success";
    }
}
