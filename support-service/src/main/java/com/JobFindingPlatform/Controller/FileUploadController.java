package com.JobFindingPlatform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.JobFindingPlatform.Service.FileUploadService;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@PostMapping("/resume")
	public ResponseEntity<String>uploadResume(@RequestParam("file") MultipartFile file ) throws Exception{
		return ResponseEntity.ok(fileUploadService.uploadFile(file, "resume"));
	}
	@PostMapping("/image")
	public ResponseEntity<String>uploadImage(@RequestParam("file") MultipartFile file )throws Exception {
		return ResponseEntity.ok(fileUploadService.uploadFile(file, "certificate"));
	}
	

}
