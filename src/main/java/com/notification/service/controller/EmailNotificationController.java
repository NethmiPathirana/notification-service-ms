package com.notification.service.controller;

import com.notification.service.dtos.EmailRequestDTO;
import com.notification.service.service.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health-sync")
public class EmailNotificationController {
    private final EmailNotificationService emailNotificationService;

    @Autowired
    public EmailNotificationController(EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmailNotification(
            @RequestParam String toEmail,
            @RequestBody EmailRequestDTO emailRequestDTO) { // Body content

        boolean emailSent = emailNotificationService.sendEmail(emailRequestDTO, toEmail);
        if (emailSent) {
            return new ResponseEntity<>("Email sent successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to send email.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
