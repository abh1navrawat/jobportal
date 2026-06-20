package com.JobFindingPlatform.Service;

import org.springframework.stereotype.Service;

import com.JobFindingPlatform.DTO.ApplicationStatusDTO;
import com.JobFindingPlatform.DTO.JobPostStatusDTO;
import com.JobFindingPlatform.DTO.UserStatusDTO;



@Service
public class DashBoardService {
	
	public JobPostStatusDTO fetechJobStatus() {
		JobPostStatusDTO jobs = new JobPostStatusDTO();
		
		jobs.totalJobs(120);
		jobs.totalInternships(70);
		jobs.totalPartTimeJobs(70);
		jobs.totalFullTimeJobs(50);
		jobs.totalContractualJobs(50);
		jobs.totalFreelanceJobs(50);
		
		return jobs;
		
	}
	
	public ApplicationStatusDTO fetchApplicationStatus() {
		
		ApplicationStatusDTO applicant = new ApplicationStatusDTO();
		
		applicant.totalApplications(500);
		applicant.totalShortlisted(100);
		applicant.totalRejected(150);
		applicant.totalPending(200);
		
		return applicant;
		
	}
	
	public UserStatusDTO fetchUsersStatus() {
		
		UserStatusDTO users = new UserStatusDTO();
		
		users.totalJobseekers(10000);
		users.totalRecruiters(500);
		users.totalBlockUsers(1000);
		users.totalPaidUsers(500);
		
		return users;
	}

}
