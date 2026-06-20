package com.jobfind.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
	
	@Autowired
	private JWTAuthenticationFilter jwtFilter;
	
	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				// Auth Service Routing (Auth, JobSeeker, Recruiter, Admin)
				.route("auth-routes", r -> r.path("/api/auth/**")
                        .uri("lb://auth-service"))
                .route("jobseeker-routes", r -> r.path("/api/job_Seekers/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://auth-service"))
                .route("recruiter-routes", r -> r.path("/api/recruiters/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://auth-service"))
                .route("admin-routes", r -> r.path("/api/admins/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://auth-service"))

				// Job Service Routing (JobPosts, Applications)
                .route("jobpost-routes", r -> r.path("/api/jobPosts/**", "/api/jobPost/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://job-service"))
                .route("application-routes", r -> r.path("/api/applications/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://job-service"))

				// Support Service Routing (Emails, Uploads, Payments, Dashboards)
                .route("email-routes", r -> r.path("/api/notification/**", "/api/notifications/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://support-service"))
                .route("fileupload-routes", r -> r.path("/api/upload/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://support-service"))
                .route("payment-routes", r -> r.path("/api/payment/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://support-service"))
                .route("dashboard-routes", r -> r.path("/api/dashboards/**", "/api/dashboard/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://support-service"))
                .build();
						
	}
	
	@Bean
	public org.springframework.web.cors.reactive.CorsWebFilter corsWebFilter() {
		org.springframework.web.cors.CorsConfiguration corsConfig = new org.springframework.web.cors.CorsConfiguration();
		corsConfig.addAllowedOriginPattern("*");
		corsConfig.addAllowedMethod("*");
		corsConfig.addAllowedHeader("*");
		corsConfig.setAllowCredentials(true);
		
		org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		
		return new org.springframework.web.cors.reactive.CorsWebFilter(source);
	}
}
