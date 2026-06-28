package com.JobFindingPlatform.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JobFindingPlatform.DTO.RecruiterDTO;
import com.JobFindingPlatform.Entity.Recruiter;
import com.JobFindingPlatform.Repository.RecruiterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruiterService {
	
	private final RecruiterRepository recruiterRepo;
	
	public RecruiterDTO createRecruiterProfile(RecruiterDTO dto) {
		Recruiter recruiter = new Recruiter();
		
		recruiter.setRecruiterName(dto.getRecruiterName());
		recruiter.setRecruiterEmail(dto.getRecruiterEmail());
		recruiter.setCompanyName(dto.getCompanyName());
		recruiter.setPhone(dto.getPhone());
		recruiter.setDesignation(dto.getDesignation());
		
		Recruiter saved=recruiterRepo.save(recruiter);
		
		
		return mapToDTO(saved);
	}
	public Optional<RecruiterDTO>getRecruiterByEmail(String email){
		return recruiterRepo.findByRecruiterEmail(email).map(this::mapToDTO);
	}
	public Optional<RecruiterDTO>getRecruiterById(Long id){
		return recruiterRepo.findById(id).map(this::mapToDTO);
	}
	
	private RecruiterDTO mapToDTO (Recruiter req) {
		
		RecruiterDTO dto = new RecruiterDTO();
		dto.setRecruiterName(req.getRecruiterName());
		dto.setPhone(req.getPhone());
		dto.setRecruiterEmail(req.getRecruiterEmail());
		dto.setCompanyName(req.getCompanyName());
		dto.setDesignation(req.getDesignation());
		
		return dto;
	}
	

}
