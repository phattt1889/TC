/**
 * 
 */
package com.ttp.TradeCoin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ttp.TradeCoin.service.SecurityService;

/**
 * @author phattt
 *
 */
@Service
public class SecurityServiceImpl implements SecurityService {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	private static Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

	@Override
	public String findLoggedInUsername() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (userDetails instanceof UserDetails) {
			return ((UserDetails) userDetails).getUsername();
		}

		return null;
	}

	@Override
	public void autologin(String userId, String password) {
		
		// get user detail
		UserDetails userDetail = userDetailsService.loadUserByUsername(userId);
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
															userDetail, password, userDetail.getAuthorities());
		
		// authenticate
		authenticationManager.authenticate(token);
		
		// check login
		if (token.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(token);
			
			logger.info("Login success");
		}
	}
}
