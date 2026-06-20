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

import com.JobFindingPlatform.DTO.JobSeekerDTO;
import com.JobFindingPlatform.Service.JobSeekerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/job_Seekers")
@RequiredArgsConstructor
public class JobSeekerController {
	
	
	@Autowired
	private JobSeekerService jobSeekerService;
	
	
	@PostMapping
	public ResponseEntity<JobSeekerDTO>createProfile(@RequestBody JobSeekerDTO dto){
		return ResponseEntity.ok(jobSeekerService.createJobseekerProfile(dto));
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<Optional<JobSeekerDTO>> getJobSeekerByEmail(@PathVariable String email){
		return ResponseEntity.ok(jobSeekerService.getJobSeekerByEmail(email));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<JobSeekerDTO>>geyJobSeekerById(@PathVariable Long id){
		return ResponseEntity.ok(jobSeekerService.getJobSeekerById(id));
	}
}
