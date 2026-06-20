package com.jobfind.gateway.security;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {
	
	private final String secret = "jobfindingKey";
	
	public String extractUserName(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
