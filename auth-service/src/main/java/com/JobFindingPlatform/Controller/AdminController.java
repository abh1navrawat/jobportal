package com.JobFindingPlatform.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JobFindingPlatform.DTO.AdminDTO;
import com.JobFindingPlatform.Entity.Admin;
import com.JobFindingPlatform.Service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {
	
	
	@Autowired
	private AdminService adminService;
	
	
	@PostMapping("/action")
	public ResponseEntity<Admin>perfoemAction(@RequestBody AdminDTO dto){
		return ResponseEntity.ok(adminService.performanceAction(dto));
	}

	@GetMapping("/admin/{adminId}")
	public ResponseEntity<List<Admin>>getLogByAdmin(@PathVariable Long adminId){
		return ResponseEntity.ok(adminService.getactionsByAdmin(adminId));
	}
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Admin>>getLogByUser(@PathVariable Long userId){
		return ResponseEntity.ok(adminService.getActionsByUser(userId));
	}
}
