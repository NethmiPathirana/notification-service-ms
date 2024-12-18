package com.notification.service;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.service.controller.EmailNotificationController;
import com.notification.service.dtos.EmailRequestDTO;
import com.notification.service.service.EmailNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class EmailNotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmailNotificationService emailNotificationService;

    @InjectMocks
    private EmailNotificationController emailNotificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(emailNotificationController).build();
    }

    @Test
    void sendEmailNotification_Success() throws Exception {
        // Arrange
        String toEmail = "test@example.com";
        EmailRequestDTO emailRequestDTO = new EmailRequestDTO();
        emailRequestDTO.setDoctorName("Dr. John");
        emailRequestDTO.setAppointmentDate("2024-12-20");
        emailRequestDTO.setAppointmentTime("10:00 AM");
        emailRequestDTO.setAppointmentNumber("12345");

        when(emailNotificationService.sendEmail(any(EmailRequestDTO.class), eq(toEmail)))
                .thenReturn(true);

        // Act and Assert
        mockMvc.perform(post("/health-sync/send-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("toEmail", toEmail)
                        .content(new ObjectMapper().writeValueAsString(emailRequestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void sendEmailNotification_Failure() throws Exception {
        // Arrange
        String toEmail = "test@example.com";
        EmailRequestDTO emailRequestDTO = new EmailRequestDTO();
        emailRequestDTO.setDoctorName("Dr. John");
        emailRequestDTO.setAppointmentDate("2024-12-20");
        emailRequestDTO.setAppointmentTime("10:00 AM");
        emailRequestDTO.setAppointmentNumber("12345");

        when(emailNotificationService.sendEmail(any(EmailRequestDTO.class), eq(toEmail)))
                .thenReturn(false);

        // Act and Assert
        mockMvc.perform(post("/health-sync/send-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("toEmail", toEmail)
                        .content(new ObjectMapper().writeValueAsString(emailRequestDTO)))
                .andExpect(status().isInternalServerError());
    }
}
