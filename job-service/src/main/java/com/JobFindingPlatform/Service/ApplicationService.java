package com.JobFindingPlatform.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JobFindingPlatform.DTO.ApplicationDTO;
import com.JobFindingPlatform.Entity.Application;
import com.JobFindingPlatform.Repository.ApplicationRepository;
import com.JobFindingPlatform.Enum.ApplicationStatus;
import com.JobFindingPlatform.Enum.JobType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {
	
	private final ApplicationRepository appRepo;
	
	
	public String apply(ApplicationDTO dto) {
		
		if(appRepo.findByJobSeekerEmailAndJobId(dto.getJobSeekerEmail(), dto.getJobId()).isPresent()) {
			throw new RuntimeException("You already have applied this job");
		}
		
		Application app= new Application();
		app.setJobId(dto.getJobId());
		app.setJobSeekerName(dto.getJobseekerName());
		app.setJobSeekerEmail(dto.getJobSeekerEmail());
		app.setJobTitle(dto.getJobTitle());
		app.setJobType(dto.getJobType());
		app.setRecruiterEmail(dto.getRecruiterEmail());
		app.setStatus(dto.getStatus());
		app.setAppliedAt(dto.getAppliedAt());
		
		appRepo.save(app);
		
		return "You just applied this job ";
		
	}
	
	public List<ApplicationDTO>getByJobSeekerEmail(String jobSeekerEmail){
		
		return appRepo.findByJobSeekerEmail(jobSeekerEmail).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	public List<ApplicationDTO>getByRecruiterEmail(String recruiterEmail){
		return appRepo.findByRecruiterEmail(recruiterEmail).stream().map(this::mapToDTO).collect(Collectors.toList());
	
	}
    public List<ApplicationDTO>getByJobTitle(String jobTitle){
		
		return appRepo.findByJobTitle(jobTitle).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
    public List<ApplicationDTO>getByJobType(JobType jobType){
		
		return appRepo.findByJobType(jobType).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	public void updateStatus(Long id,ApplicationStatus status){
		Application app = appRepo.findById(id).orElseThrow(()-> new RuntimeException("Application not found"));
		app.setStatus(status);
		appRepo.save(app);
	}
	
	private ApplicationDTO mapToDTO(Application app) {
		
		ApplicationDTO dto = new ApplicationDTO();
		dto.setJobId(app.getJobId());
		dto.setJobSeekerEmail(app.getJobSeekerEmail());
		dto.setJobseekerName(app.getJobSeekerName());
		dto.setRecruiterEmail(app.getRecruiterEmail());
		dto.setJobType(app.getJobType());
		dto.setJobTitle(app.getJobTitle());
		dto.setAppliedAt(app.getAppliedAt());
		dto.setStatus(app.getStatus());
		
		return dto;
	}
	

}
