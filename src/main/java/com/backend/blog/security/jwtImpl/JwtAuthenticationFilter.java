package com.backend.blog.security.jwtImpl;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend.blog.entities.User;
import com.backend.blog.security.CustomUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private final JwtTokenHelper jwtTokenHelper;
	private final CustomUserDetailsService userDetailsService;
	
	public JwtAuthenticationFilter(JwtTokenHelper jwtTokenHelper, CustomUserDetailsService userDetailsService) {
		super();
		this.jwtTokenHelper = jwtTokenHelper;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestToken = request.getHeader("Authorization");
		System.out.println(requestToken);
		String username = null;
		String token = null;
		
		//get token
		if (requestToken!=null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
			try {
				username = this.jwtTokenHelper.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get Jwt token !");
			}catch (ExpiredJwtException e) {
				System.out.println("Jwt token has Expired !");
			}catch (MalformedJwtException e) {
				System.out.println("Invalid Jwt !");
			}
		}else {
			System.out.println("Jwt token does not begin with Bearer !");
		}
		
		//validate token
		if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if (this.jwtTokenHelper.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails
				(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
				System.out.println("Invalid Jwt token !");
			}
		}else {
			System.out.println("username is null or context is not null !");
		}
		
		filterChain.doFilter(request, response);
	}

	/*
	 * private String extractJwtFromRequest(HttpServletRequest request) { // TODO
	 * Auto-generated method stub return null; }
	 */
}
