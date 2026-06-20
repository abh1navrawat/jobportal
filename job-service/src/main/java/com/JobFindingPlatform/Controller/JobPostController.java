package com.JobFindingPlatform.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.JobFindingPlatform.DTO.JobPostDTO;
import com.JobFindingPlatform.Service.JobPostService;
import com.JobFindingPlatform.Enum.JobType;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jobPost")
public class JobPostController {
	
	@Autowired
	private JobPostService jobPostService;
	
	
	@PostMapping 
	public ResponseEntity<String >createJobPost(@RequestBody JobPostDTO dto){
		return ResponseEntity.ok(jobPostService.createJobPost(dto));
	}
	@GetMapping("/all")
	public ResponseEntity<List<JobPostDTO>>getAllJobs(){
		return ResponseEntity.ok(jobPostService.getAllJobs());
	}
	@GetMapping("/postedBy")
	public ResponseEntity<List<JobPostDTO>>getjobByrecruiter(@RequestParam String postedBy){
		return ResponseEntity.ok(jobPostService.getJobByPostedBy(postedBy));
	}
	@GetMapping("/search/{companyName}")
	public ResponseEntity<List<JobPostDTO>>getJobByCompanyName(@PathVariable String companyName){
		return ResponseEntity.ok(jobPostService.getJobByCompanyName(companyName));
	}
	@GetMapping("/search/{jobType}")
	public ResponseEntity<List<JobPostDTO>>getJobByJobType(@PathVariable JobType jobType){
		return ResponseEntity.ok(jobPostService.getJobByJobType(jobType));
	}
	@GetMapping("/search/{jobTitle}")
	public ResponseEntity<List<JobPostDTO>>getJobByJobTitle(@PathVariable String jobTitle){
		return ResponseEntity.ok(jobPostService.getJobByJobTitle(jobTitle));
	}
	@GetMapping("/serach/{jobLocation}")
	public ResponseEntity<List<JobPostDTO>>getJobByJobLocation(@PathVariable String jobLocation){
		return ResponseEntity.ok(jobPostService.getJobByJobLocation(jobLocation));
	}
	@GetMapping("/search/{remote}")
	public ResponseEntity<List<JobPostDTO>>getJobByRemote(@PathVariable String remote){
		return ResponseEntity.ok(jobPostService.getJobByRemote(remote));
	}
	@PostMapping("/close/{id}")
	public ResponseEntity<String>close(@PathVariable Long id){
		jobPostService.closeJob(id);
		return ResponseEntity.ok("Job got Closed");
	}

}
