package com.sjs.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjs.SpringApplicationContext;
import com.sjs.model.request.UserLoginRequestModel;
import com.sjs.services.UserSevice;
import com.sjs.shared.dto.UserDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	public final AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UserLoginRequestModel creds = new ObjectMapper().readValue(request.getInputStream(),
					UserLoginRequestModel.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	 @Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String userName =((User) authResult.getPrincipal()).getUsername();
//		String tokenSecret =new SecurityConstants().getTokenSecret();
		
		String token =Jwts.builder()
				      .setSubject(userName)
				      .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPRITION_TIME))
				      .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
				      .compact();
		
		UserSevice userSevice =(UserSevice) SpringApplicationContext.getBean("userServiceImpl");
		UserDto userDto = userSevice.getUser(userName);
      response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);		
	  response.addHeader("UserId", userDto.getUserId());
	 }
	
}
