package com.JobFindingPlatform.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JobFindingPlatform.Entity.JobPost;
import com.JobFindingPlatform.Enum.JobType;

import java.util.List;



@Repository
public interface JobPostRepository extends JpaRepository<JobPost,Long>{
	List<JobPost> findByPostedBy(String postedBy);
	List<JobPost> findByCompanyName(String companyName);
	List<JobPost> findByJobTitle(String jobTitle);
	List<JobPost>findByJobType(JobType jobType);
	List<JobPost> findByJobLocation(String jobLocation);
	List<JobPost> findByRemote(String remote);
}

