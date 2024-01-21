package com.sam.exception;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorModel {
	
	private String code;
	private String message;
	private LocalDateTime timestamp;


}
