package com.jvb_intern.rental_acommodation.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jvb_intern.rental_acommodation.entity.TenantDating;
import com.jvb_intern.rental_acommodation.exception.NotFoundDatingException;
import com.jvb_intern.rental_acommodation.service.LandlordDatingService;
import com.jvb_intern.rental_acommodation.service.TenantDatingService;

@Controller
public class TenantDatingController {
    @Autowired
    private TenantDatingService tenantDatingService;

    @Autowired
    private LandlordDatingService landlordDatingService;

    @GetMapping("tenant/tenant-queue")
    public String showWaitingDatings(Model model) {
        Authentication authTenant = SecurityContextHolder.getContext().getAuthentication();

        List<TenantDating> tenantWatingsDating = tenantDatingService.getWaitDatings(authTenant);
        model.addAttribute("waitingDatings", tenantWatingsDating);

        List<TenantDating> tenantCanceledDatings = tenantDatingService.getCanceledDatings(authTenant);
        model.addAttribute("canceledDatings", tenantCanceledDatings);
        return "6-tenant-queue";
    }

    @PostMapping("/tenant/tenant-queue")
    public String cancelBooking(@RequestParam(name = "datingId", required = false) Long datingId,
            @RequestParam(name = "bookingDate", required = false) String bookingDateString,
            @RequestParam(name = "bookingTime", required = false) String bookingTimeString,
            @RequestParam(name = "tenantId", required = false) Long tenantId,
            @RequestParam(name = "landlordId", required = false) Long landlordId,
            @RequestParam(name = "actionCancel", required = false) String actionDating, 
            @RequestParam(name = "actionRebook", required = false) String actionRebook) throws NotFoundDatingException {

        if(datingId == null || bookingDateString == null || bookingTimeString == null || tenantId == null || landlordId == null) {
            return "redirect:/tenant/tenant-queue?failCancel";
        }

        // Nếu nhấn nút đặt lại
        if(actionRebook.equals("rebookDating")) {
            TenantDating bookedDating = tenantDatingService.rebook(datingId); // set up lại isCanceled
            landlordDatingService.saveTenantDating(bookedDating);
            return "redirect:/tenant/tenant-queue?rebookSuccess";
        }

        if(actionDating.equals("cancel")) {
            // Chuyển kiểu dữ liệu String sang LocalDate và LocalTime
            LocalDate parsedBookingDate = LocalDate.parse(bookingDateString);
            LocalTime parsedBookingTime = LocalTime.parse(bookingTimeString);

            // Huỷ lịch
            tenantDatingService.cancelDating(datingId);
            landlordDatingService.cancelByTenant(tenantId, landlordId, parsedBookingDate, parsedBookingTime);
            return "redirect:/tenant/tenant-queue?successCancel";
        }
        return "6-tenant-queue";
    }

    @GetMapping("/tenant/tenant-date")
    public String showDating(Model model) {
        Authentication authTenant = SecurityContextHolder.getContext().getAuthentication();
        List<TenantDating> listSuccessDating = tenantDatingService.getSuccessDating(authTenant);

        model.addAttribute("confirmDatings", listSuccessDating);
        return "4-tenant-date";
    }
}
