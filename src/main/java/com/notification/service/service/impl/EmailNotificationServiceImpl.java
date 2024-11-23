package com.notification.service.service.impl;

import com.notification.service.dtos.EmailRequestDTO;
import com.notification.service.service.EmailNotificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public EmailNotificationServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public boolean sendEmail(EmailRequestDTO appointmentDetails, String toEmail) {
        try {
            String subject = "HealthSync Notification";
            String body = buildEmailBody(appointmentDetails);

            // Create email message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setSubject(subject);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(toEmail);
            messageHelper.setText(body, true);

            // Send email
            javaMailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String buildEmailBody(EmailRequestDTO appointmentDetails) {
        return "<html><body>"
                + "<p><strong>Dear Patient,</strong></p>"
                + "<p>Your appointment details are as follows:</p>"
                + "<p><strong>Doctor:</strong> " + appointmentDetails.getDoctorName() + "</p>"
                + "<p><strong>Appointment Date:</strong> " + appointmentDetails.getAppointmentDate() + "</p>"
                + "<p><strong>Appointment Time:</strong> " + appointmentDetails.getAppointmentTime() + "</p>"
                + "<p><strong>Appointment Number:</strong> " + appointmentDetails.getAppointmentNumber() + "</p>"
                + "<p>Thank you for choosing HealthSync.</p>"
                + "</body></html>";
    }
}
