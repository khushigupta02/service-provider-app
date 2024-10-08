package com.example.myApplication.JWT;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.myApplication.Service.JWTService.JwtService;
import com.example.myApplication.Service.JWTService.UserInfoService;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserInfoService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String userName = null;
		if ((authHeader != null) && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			userName = jwtService.extractUsername(token);
		}

		if ((userName != null) && (SecurityContextHolder.getContext()
														.getAuthentication() == null)) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
			if (jwtService.validateToken(token, userDetails)) {
				var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext()
									 .setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}