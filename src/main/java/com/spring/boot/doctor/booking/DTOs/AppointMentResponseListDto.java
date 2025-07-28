package com.spring.boot.doctor.booking.DTOs;

import java.time.LocalDateTime;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointMentResponseListDto {
	  private Long patientId;
	  private String patientName;
	  private LocalDateTime scheduledDateTime;
	  private String type;
	  private String status;
}
