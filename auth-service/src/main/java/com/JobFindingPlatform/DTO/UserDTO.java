package com.JobFindingPlatform.DTO;

import com.JobFindingPlatform.Enum.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
	
	private String userName;
	private String userEmail;
	private String password;
	private Role role;
	
//	public UserDTO() {}
//	public UserDTO(String userName, String UserEmail,String password, Role role) {
//		this.userName= userName;
//		this.userEmail=userEmail;
//		this.password=password;
//		this.role=role;
//	}
//	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
	

}

