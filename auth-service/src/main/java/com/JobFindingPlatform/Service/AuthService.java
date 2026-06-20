package com.JobFindingPlatform.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.JobFindingPlatform.DTO.AuthResponseDTO;
import com.JobFindingPlatform.DTO.LoginRequestDTO;
import com.JobFindingPlatform.DTO.UserDTO;
import com.JobFindingPlatform.Entity.User;
import com.JobFindingPlatform.Security.JWTUtil;
import com.JobFindingPlatform.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public AuthResponseDTO register(UserDTO dto) {
		
		if(userRepository.findByUserEmail(dto.getUserEmail()).isPresent()) {
			throw new RuntimeException("Emial already exist");
		}
		User user = new User();
		
		user.setUserName(dto.getUserName());
		user.setUserEmail(dto.getUserEmail());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole(dto.getRole());
		userRepository.save(user);
		
		String token = jwtUtil.generateToken(user);
		return new AuthResponseDTO(token,"User got register");
		
	}
	
	public String login(LoginRequestDTO dto) {
		
		 User user = userRepository.findByUserEmail(dto.getUserEmail()).orElseThrow(() -> new RuntimeException("User not found"));
		 
		 if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			 
		 
			 throw new RuntimeException("Invalid credentials");
		 }
		 
	  return jwtUtil.generateToken(user);
    }
	
	
}

