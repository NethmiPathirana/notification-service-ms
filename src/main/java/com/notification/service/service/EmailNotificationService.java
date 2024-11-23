package com.notification.service.service;

import com.notification.service.dtos.EmailRequestDTO;

public interface EmailNotificationService {
    boolean sendEmail(EmailRequestDTO emailRequest, String toEmail);
}
