package com.JobFindingPlatform.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JobFindingPlatform.DTO.JobSeekerDTO;
import com.JobFindingPlatform.Entity.JobSeeker;
import com.JobFindingPlatform.Repository.JobSeekerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobSeekerService {
	
	@Autowired
	private JobSeekerRepository jobSeekerRepo;
	
	
	
	public JobSeekerDTO createJobseekerProfile(JobSeekerDTO dto) {
		
		JobSeeker jobseeker = new JobSeeker();
		
		jobseeker.setFullName(dto.getFullName());
		jobseeker.setEmail(dto.getEmail());
		jobseeker.setPhone(dto.getPhone());
		jobseeker.setColleageName(dto.getColleageName());
		jobseeker.setUniversityName(dto.getUniversityName());
		jobseeker.setPassingYear(dto.getPassingYear());
		jobseeker.setDegree(dto.getDegree());
		jobseeker.setResumeURL(dto.getResumeURL());
		
		
		jobSeekerRepo.save(jobseeker);
		return dto;
		
	}
	
	
	public Optional<JobSeekerDTO>getJobSeekerByEmail(String email){
		
		return jobSeekerRepo.findByEmail(email).map(jobSeek ->{
			                                     
			                                    JobSeekerDTO dto = new JobSeekerDTO();
			                                    dto.setFullName(jobSeek.getFullName());
			                                    dto.setEmail(jobSeek.getEmail());
			                                    dto.setPhone(jobSeek.getPhone());
			                                    dto.setColleageName(jobSeek.getColleageName());
			                                    dto.setUniversityName(jobSeek.getUniversityName());
			                                    dto.setPassingYear(jobSeek.getPassingYear());
			                                    dto.setDegree(jobSeek.getDegree());
			                                    dto.setResumeURL(jobSeek.getResumeURL());
			                                    return dto;
			                                    		
		                                            });
	}
	
	public Optional<JobSeekerDTO>getJobSeekerById(Long id){
		
		return jobSeekerRepo.findById(id).map(jobSeeks->{
			
			JobSeekerDTO dto = new JobSeekerDTO();
            dto.setFullName(jobSeeks.getFullName());
            dto.setEmail(jobSeeks.getEmail());
            dto.setPhone(jobSeeks.getPhone());
            dto.setColleageName(jobSeeks.getColleageName());
            dto.setUniversityName(jobSeeks.getUniversityName());
            dto.setPassingYear(jobSeeks.getPassingYear());
            dto.setDegree(jobSeeks.getDegree());
            dto.setResumeURL(jobSeeks.getResumeURL());
            return dto;
            		 });
	}

}