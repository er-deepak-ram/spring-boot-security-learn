package com.learn.springbootsecuritylearn.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Get jwt token from request
		String requestToken = request.getHeader("Authorization");

		// Bearer 2352523sdgsg
		String userName = null;
		String token = null;
		if (request != null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
			try {
				userName = jwtTokenHelper.getUserNameFromToken(token);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get Jwt token");
			} catch (ExpiredJwtException e) {
				System.out.println("Jwt token has expired");
			} catch (MalformedJwtException e) {
				System.out.println("Invalid Jwt");
			}
		} else {
			System.out.println("Jwt token does not begin with Bearer");
		}

		// Once we get the token, now we can validate it
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
			if (jwtTokenHelper.validateToken(token, userDetails)) {
				// If the control is inside then its validated now we'll set authentication
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				System.out.println("Invalid jwt token");
			}
		} else {
			System.out.println("username is null or context is  not null");
		}

		filterChain.doFilter(request, response);
	}

}
