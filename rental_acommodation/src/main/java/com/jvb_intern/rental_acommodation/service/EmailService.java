package com.jvb_intern.rental_acommodation.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

public class EmailService {
    @Value("${spring.sendgrid.api-key}")
    private String sendGridApiKey;

    public void sendEmail(String recipientEmail, String link) throws IOException {
        Email from = new Email("timtro247@.com"); // Điền email của bạn
        String subject = "[Đặt lại mật khẩu] Link đặt lại mật khẩu";
        Email to = new Email(recipientEmail);
        Content content = new Content("text/html", "<p>Hệ thống Timtro247 xin chào,</p>"
                + "<p>Bạn vừa gửi yêu cầu đặt lại mật khẩu</p>"
                + "<p>Vui lòng nhấn vào link dưới đây để có thể đặt lại mật khẩu của mình. Xin cảm ơn</p>"
                + "<p><a href=\"" + link + "\">Đặt lại mật khẩu</a></p>"
                + "<br>"
                + "<p>Vui lòng bỏ qua thư nếu bạn không thực hiện yêu cầu này!!</p>");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("Email sent, response: " + response.getBody());
        } catch (IOException ex) {
            throw ex;
        }
    }
}
