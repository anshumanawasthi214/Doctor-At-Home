package com.spring.boot.doctor.booking.UTIL;

import java.util.Date;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.spring.boot.doctor.booking.ENTITY.Users;
import com.spring.boot.doctor.booking.REPOSITORY.UsersRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
	private final int EXTRA_TIME=1000*60*60;
	private final String SECRET_KEY="ahfhghhf;lahfohflkadshfahf;ldahf;asdhfdalfahg9y859hff2393678@$%";
	private final SecretKey key=Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	
	
	@Autowired
	UsersRepository userRepo;
	
	public String generateToken(String username) {
	    Users user = userRepo.findByUsername(username)
	        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	    Map<String, Object> claims = new HashMap<>();
	    claims.put("role", user.getRole()); // avoid hardcoding "ADMIN"
	    claims.put("userId", user.getId()); // 👈 include the user's ID

	    return Jwts.builder()
	        .claims(claims)
	        .subject(username)
	        .issuedAt(new Date(System.currentTimeMillis()))
	        .expiration(new Date(System.currentTimeMillis() + EXTRA_TIME))
	        .signWith(key, Jwts.SIG.HS256)
	        .compact();
	}
	
	public Long extractUserId(String token) {
	    return Jwts.parser()
	    					.verifyWith(key)
	    					.build()
	    					.parseSignedClaims(token)
	    					.getPayload()
	    					.get("userId",Long.class);
	}
	
	  public String extractUsername(String token) {
		  if (token == null || token.trim().isEmpty()) {
		        throw new IllegalArgumentException("JWT token cannot be null or empty");
		    }
		  return Jwts.parser()
	              .verifyWith(key)              // ✅ new way
	              .build()
	              .parseSignedClaims(token)     // for tokens with a signature
	              .getPayload()
	              .getSubject();                // return the "sub" claim
	}

	public boolean validateToken(UserDetails userDetails,String token) {
		//Check if username is same as 
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
		}
	
	
	public boolean isTokenExpired(String token) {
		  return Jwts.parser()
	              .verifyWith(key)              // ✅ new way
	              .build()
	              .parseSignedClaims(token)     // for tokens with a signature
	              .getPayload()
	              .getExpiration()
	              .before(new Date(System.currentTimeMillis()));    
	}
	
	public String extractRole(String token) {
	    return Jwts.parser()
	            .verifyWith(key)
	            .build()
	            .parseSignedClaims(token)
	            .getPayload()
	            .get("role", String.class);
	}
}
