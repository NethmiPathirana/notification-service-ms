package com.notification.service.dtos;

import lombok.Data;

@Data
public class EmailRequestDTO {
    private String doctorName;
    private String appointmentDate;
    private String appointmentTime;
    private String appointmentNumber;
}
