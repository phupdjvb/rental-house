package com.jvb_intern.rental_acommodation.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jvb_intern.rental_acommodation.common.Constant;
import com.jvb_intern.rental_acommodation.entity.Landlord;
import com.jvb_intern.rental_acommodation.entity.Tenant;
import com.jvb_intern.rental_acommodation.service.LandlordService;
import com.jvb_intern.rental_acommodation.service.TenantService;

import net.bytebuddy.utility.RandomString;

@Controller
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private LandlordService landlordService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password-form";
    }

    @PostMapping("forgot-password")
    public String processForgetPassword(HttpServletRequest request, Model model)
            throws MessagingException, IOException {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        if (tenantService.existByEmail(email)) {
            tenantService.updateResetPasswordToken(token, email);
            String resetPasswordLink = getSiteURL(request) + "/reset-password?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute(Constant.MESSAGE, "Hệ thống đã gửi mail đặt lại mật khẩu cho bạn. Vui lòng kiểm tra hộp thư!");
        } else if (landlordService.existByEmail(email)) {
            landlordService.updateResetPasswordToken(token, email);
            String resetPasswordLink = getSiteURL(request) + "/reset-password?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute(Constant.MESSAGE, "Hệ thống đã gủi mail đặt lại mật khẩu cho bạn. Vui lòng kiểm tra hộp thư!");
        }
        return "forgot-password-form";
    }

    private void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("phamduyphu_t65@hus.edu.vn", "Hệ thống phòng trọ 247 - Trung tâm hỗ trợ");
        helper.setTo(recipientEmail);

        String subject = "[Đặt lại mật khẩu] Link đặt lại mật khẩu";
        String content = "<p>Hệ thống Timtro247 xin chào,</p>"
                + "<p>Bạn vừa gửi yêu cầu đặt lại mật khẩu</p>"
                + "<p>Vui lòng nhấn vào link dưới đây để có thể đặt lại mật khẩu của mình. Xin cảm ơn</p>"
                + "<p><a href=\"" + link + "\">Đặt lại mật khẩu</a></p>"
                + "<br>"
                + "<p>P/s: Vui lòng bỏ qua thư này nếu bạn không thực hiện yêu cầu này!";

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), ""); // get root url, return : localhost:8080
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        Landlord landlord = landlordService.findByResetPasswordToken(token);
        if (landlord != null) {
            model.addAttribute(Constant.TOKEN, token);
            return "reset-password-form";
        }

        Tenant tenant = tenantService.findByResetPasswordToken(token);
        if (tenant != null) {
            model.addAttribute(Constant.TOKEN, token);
            return "reset-password-form";
        }
        
        model.addAttribute(Constant.MESSAGE, "Liên kết đã hết hạn hoặc tài khoản không tồn tại!");
        return "message";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(HttpServletRequest request, Model model) {

        String token = request.getParameter("token");
        String password = request.getParameter("password");

        Tenant tenant = tenantService.findByResetPasswordToken(token);
        Landlord landlord = landlordService.findByResetPasswordToken(token);

        if (tenant == null && landlord == null) {
            model.addAttribute(Constant.MESSAGE, "Xác thực không hợp lệ!");
            return "message";
        } else {
            if (tenant != null) {
                tenantService.updatePassword(tenant, password);
                model.addAttribute(Constant.MESSAGE, "Đặt lại mật khẩu thành công");
            } else {
                landlordService.updatePassword(landlord, password);
                model.addAttribute(Constant.MESSAGE, "Đặt lại mật khẩu thành công");
            }
        }
        return "message";
    }
}
