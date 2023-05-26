package com.backend.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend.blog.security.CustomUserDetailsService;
import com.backend.blog.security.jwtImpl.JwtAuthenticationEntryPoint;
import com.backend.blog.security.jwtImpl.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	private final CustomUserDetailsService customUserDetailsService;
	private final JwtAuthenticationEntryPoint jwtAuthEntryPoint;
	private final JwtAuthenticationFilter jwtAuthFilter;

	public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtAuthenticationEntryPoint jwtAuthEntryPoint, JwtAuthenticationFilter jwtAuthFilter) {
		super();
		this.customUserDetailsService = customUserDetailsService;
		this.jwtAuthEntryPoint = jwtAuthEntryPoint;
		this.jwtAuthFilter = jwtAuthFilter;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http. csrf().disable()
		.authorizeHttpRequests()
		  .antMatchers("/api/auth/blog/**").permitAll() 
		  .anyRequest()
		  .authenticated()
		  .and()
		  .exceptionHandling().authenticationEntryPoint(this.jwtAuthEntryPoint)
		  .and()
		  .sessionManagement()
		  .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.customUserDetailsService)
		.passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
}
