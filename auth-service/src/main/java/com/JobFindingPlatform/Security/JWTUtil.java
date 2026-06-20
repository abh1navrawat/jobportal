package com.JobFindingPlatform.Security;


import java.util.Date;
import org.springframework.stereotype.Component;
import com.JobFindingPlatform.Entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	private final String secret = "jobfindingKey";
	private final long expirationTime= 86400000;
	
	public String generateToken(User user) {
		

		return  Jwts.builder()
				.setSubject(user.getUserEmail())
				.claim("role",user.getRole().toString())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+expirationTime))
				.signWith(SignatureAlgorithm.HS512,secret )
				.compact();
	}
	
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
