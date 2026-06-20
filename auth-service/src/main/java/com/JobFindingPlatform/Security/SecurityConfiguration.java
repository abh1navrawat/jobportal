package com.JobFindingPlatform.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(authorize -> authorize
	            .requestMatchers("/api/auth/**",
	            		"/api/job_Seekers",
	            		"/api/recruiters",
	            		"/api/jobPost",
	            		"/api/applications",
	            		"/api/admins"
	            		,"/api/notification",
	            		"/api/upload",
	            		"/api/payment",
	            		"/api/dashboards").permitAll()
	            .anyRequest().authenticated()
	        )
	        .csrf(csrf -> csrf.disable());
	    return http.build();
	}


	
	@Bean
	public PasswordEncoder passwordEncode() {
		return new  BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception {
		return config.getAuthenticationManager();
	}
	
}