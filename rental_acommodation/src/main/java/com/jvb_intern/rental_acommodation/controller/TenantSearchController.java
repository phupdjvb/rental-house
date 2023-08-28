package com.jvb_intern.rental_acommodation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jvb_intern.rental_acommodation.service.TenantHomeService;

@Controller
public class TenantSearchController {
    @Autowired
    private TenantHomeService tenantHomeService;

    @GetMapping("/tenant/tenant-search")
    public String showResultSearch(Model model, @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "6") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort) {

        Sort sortable = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, sortable);// tạo đối tượng pageble
        model.addAttribute("listResult", tenantHomeService.searchPost(keyword, pageable));
        return "22-tenant-search";
    }
}
