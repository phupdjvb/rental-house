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

import com.jvb_intern.rental_acommodation.entity.LandlordDating;
import com.jvb_intern.rental_acommodation.service.LandlordDatingService;
import com.jvb_intern.rental_acommodation.service.TenantDatingService;

@Controller
public class LandlordDatingController {
    @Autowired
    private LandlordDatingService landlordDatingService;

    @Autowired
    private TenantDatingService tenantDatingService;

    @GetMapping("/landlord/landlord-queue")
    public String showDatingQueue(Model model) {
        Authentication authLandlord = SecurityContextHolder.getContext().getAuthentication();

        List<LandlordDating> listWaitingDatings = landlordDatingService.getWaitingDatings(authLandlord);
        model.addAttribute("waitingDatings", listWaitingDatings);

        List<LandlordDating> listCanceledByLandlord = landlordDatingService.getCanceledDatingByLandlord(authLandlord);
        model.addAttribute("canceledDatings", listCanceledByLandlord);

        List<LandlordDating> listCanceledByTenant = landlordDatingService.getCanceledDatingByTenant(authLandlord);
        model.addAttribute("canceledDatingsByTenant", listCanceledByTenant);
        return "14-landlord-queue";
    }

    @PostMapping("/landlord/landlord-queue")
    public String actionDating(@RequestParam String action,
            @RequestParam("datingId") Long datingId,
            @RequestParam("tenantId") Long tenantId,
            @RequestParam("landlordId") Long landlordId,
            @RequestParam("bookingDate") String bookingDate,
            @RequestParam("bookingTime") String bookingTime) {
        if (action.equals("cancel")) {
            // Chuyển kiểu dữ liệu String sang LocalDate và LocalTime
            LocalDate parsedBookingDate = LocalDate.parse(bookingDate);
            LocalTime parsedBookingTime = LocalTime.parse(bookingTime);
            landlordDatingService.cancelDating(datingId);
            tenantDatingService.canceledByLandlord(landlordId, tenantId, parsedBookingDate, parsedBookingTime);
            return "redirect:/landlord/landlord-queue?successCancel";
        }
        if (action.equals("confirm")) {
            // Chuyển kiểu dữ liệu String sang LocalDate và LocalTime
            LocalDate parsedBookingDate = LocalDate.parse(bookingDate);
            LocalTime parsedBookingTime = LocalTime.parse(bookingTime);
            landlordDatingService.confirmDating(datingId);
            tenantDatingService.acceptConfirm(landlordId, tenantId, parsedBookingDate, parsedBookingTime);
            return "redirect:/landlord/landlord-queue?successConfirm";
        }
        return "14-landlord-queue";
    }

    @GetMapping("/landlord/landlord-date")
    public String showDating(Model model) {
        Authentication authLandlord = SecurityContextHolder.getContext().getAuthentication();

        List<LandlordDating> listSuccessDating = landlordDatingService.getSuccessDating(authLandlord);
        model.addAttribute("confirmDatings", listSuccessDating);
        return "13-landlord-date";
    }
}
