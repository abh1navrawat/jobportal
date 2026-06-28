package com.JobFindingPlatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JobFindingPlatform.DTO.EmailRequestDTO;
import com.JobFindingPlatform.Service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class EmailController {
	private final EmailService emailService;
	
	@PostMapping("/send")
	public ResponseEntity<String>sendEmail(@RequestBody EmailRequestDTO dto){
		emailService.sendEmail(dto);
		return ResponseEntity.ok("Email sent Successfully");
	}
}

