package com.JobFindingPlatform.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JobFindingPlatform.DTO.JobPostDTO;
import com.JobFindingPlatform.Entity.JobPost;
import com.JobFindingPlatform.Repository.JobPostRepository;
import com.JobFindingPlatform.Enum.JobType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobPostService {
	
	private final JobPostRepository jobPostRepo;
	
	
	public String createJobPost(JobPostDTO dto) {
		JobPost job = new JobPost();
		
		job.setCompanyName(dto.getCompanyName());
		job.setJobType(dto.getJobType());
		job.setJobCategory(dto.getJobCategory());
		job.setJobDescription(dto.getJobDescription());
		job.setJobLocation(dto.getJobLocation());
		job.setJobTitle(dto.getJobTitle());
		job.setPostedBy(dto.getPostedBy());
		job.setRemote(dto.getRemote());
		job.setPostedAt(dto.getPostedAt());
		
		jobPostRepo.save(job);
		
		return "Job got posted";
		
	}
	public List<JobPostDTO>getAllJobs(){
		return jobPostRepo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	public List<JobPostDTO>getJobByCompanyName(String companyName){
		return jobPostRepo.findByCompanyName(companyName).stream().map(this::mapToDTO).collect(Collectors.toList());
		
	}
	public List<JobPostDTO>getJobByPostedBy(String postedBy){
		return jobPostRepo.findByPostedBy(postedBy).stream().map(this::mapToDTO).collect(Collectors.toList());
		
	}
	public List<JobPostDTO>getJobByJobTitle(String jobTitle){
		return jobPostRepo.findByJobTitle(jobTitle).stream().map(this::mapToDTO).collect(Collectors.toList());
		
	}
	public List<JobPostDTO>getJobByJobType(JobType jobType){
		return jobPostRepo.findByJobType(jobType).stream().map(this::mapToDTO).collect(Collectors.toList());
		
	}
	public List<JobPostDTO>getJobByJobLocation(String jobLocation){
		return jobPostRepo.findByJobLocation(jobLocation).stream().map(this::mapToDTO).collect(Collectors.toList());
		
	}
	public List<JobPostDTO>getJobByRemote(String remote){
		return jobPostRepo.findByRemote(remote).stream().map(this::mapToDTO).collect(Collectors.toList());
		
	}
	public void closeJob(Long id) {
		JobPost job = jobPostRepo.findById(id).orElseThrow(()-> new RuntimeException("JOb not found"));
		job.setActive(false);
		jobPostRepo.save(job);
	}
	
	private JobPostDTO mapToDTO (JobPost jobPost) {
		
		JobPostDTO dto = new JobPostDTO();
		
		dto.setCompanyName(jobPost.getCompanyName());
		dto.setJobCategory(jobPost.getJobCategory());
		dto.setJobDescription(jobPost.getJobDescription());
		dto.setJobType(jobPost.getJobType());
		dto.setJobTitle(jobPost.getJobTitle());
		dto.setPostedBy(jobPost.getPostedBy());
		dto.setJobLocation(jobPost.getJobLocation());
		dto.setPostedAt(jobPost.getPostedAt());
		dto.setRemote(jobPost.getRemote());
		
		return dto;
	}

}

