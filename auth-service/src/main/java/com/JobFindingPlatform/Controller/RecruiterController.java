package com.JobFindingPlatform.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JobFindingPlatform.DTO.RecruiterDTO;
import com.JobFindingPlatform.Service.RecruiterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recruiters")
@RequiredArgsConstructor
public class RecruiterController {
	
	@Autowired
	private RecruiterService recruiterService;
	
	
	@PostMapping
	public ResponseEntity<RecruiterDTO>createRecruiterProfile(@RequestBody RecruiterDTO dto ){
		return ResponseEntity.ok(recruiterService.createRecruiterProfile(dto));
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<Optional<RecruiterDTO>>getRecruiterByEmail(@PathVariable String recruiterEmail){
		return ResponseEntity.ok(recruiterService.getRecruiterByEmail(recruiterEmail));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<RecruiterDTO>>getRecruiterById(@PathVariable Long id){
		return ResponseEntity.ok(recruiterService.getRecruiterById(id));
	}
}