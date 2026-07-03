package com.cmc.auth.config;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cmc.auth.service.CustomUserDetailsServiceImpl;
import com.cmc.util.enums.HttpErrorCode;
import com.cmc.util.enums.TokenType;
import com.cmc.util.exception.BaseException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthFilter extends OncePerRequestFilter{

	private final JwtTokenProvider jwtTokenProvider;
	private final CustomUserDetailsServiceImpl customUserDetailsServiceImpl;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		TokenType atTokenType = TokenType.AT;
		

		String authHeaders = request.getHeader("Authorization");
		String authPrefix = "Bearer ";
		if(StringUtils.hasText(authHeaders) && authHeaders.startsWith(authPrefix)) {
			String accessToken = authHeaders.substring(7,authHeaders.length());
			try {
				if(StringUtils.hasText(accessToken) && jwtTokenProvider.validateToken(accessToken, atTokenType)) {
					String userName = jwtTokenProvider.getUsernameFromToken(accessToken, atTokenType);
					
					UserDetails userDetails = customUserDetailsServiceImpl.loadUserByUsername(userName);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
					log.info("[AUTH] - Filter :: User is authorized");
				}
			} catch (Exception e) {
				throw new BaseException(HttpErrorCode.UNAUTHORIZED, "User is unauthorized");
			}
		}
		filterChain.doFilter(request, response);
	}

}
