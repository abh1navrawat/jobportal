package com.JobFindingPlatform.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JobFindingPlatform.Entity.Application;
import com.JobFindingPlatform.Enum.JobType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long>{
	List<Application>findByJobSeekerEmail(String jobSeekerEmail);
	List<Application>findByRecruiterEmail(String recruiterEmail);
	Optional<Application>findByJobSeekerEmailAndJobId(String jobSeekerEmail,Long jobId);
	Optional<Application>findByJobTitle(String jobTitle);
	Optional<Application>findByJobType(JobType jobType);
}
