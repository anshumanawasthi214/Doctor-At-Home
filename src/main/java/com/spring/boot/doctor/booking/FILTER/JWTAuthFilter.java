package com.spring.boot.doctor.booking.FILTER;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.boot.doctor.booking.SERVICE.CustomUserDetailsService;
import com.spring.boot.doctor.booking.UTIL.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
	
	@Autowired
	JWTUtil jwtUtil;
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;

		System.out.println("[JWTAuthFilter] Incoming request: " + request.getRequestURI());

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			try {
				username = jwtUtil.extractUsername(token);
				System.out.println("[JWTAuthFilter] Extracted username from token: " + username);
			} catch (Exception e) {
				System.out.println("[JWTAuthFilter] Error extracting username from token: " + e.getMessage());
				// You could optionally respond with 401 here instead of continuing the filter chain
			}
		} else {
			System.out.println("[JWTAuthFilter] No Bearer token found in Authorization header");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

			boolean isValidToken = false;
			try {
				isValidToken = jwtUtil.validateToken(	userDetails, token);
			} catch (Exception e) {
				System.out.println("[JWTAuthFilter] Token validation error: " + e.getMessage());
			}

			System.out.println("[JWTAuthFilter] Token valid: " + isValidToken);

			if (isValidToken) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
				System.out.println("[JWTAuthFilter] Security context updated with authentication");
			}
		}

		filterChain.doFilter(request, response);
	}
}
