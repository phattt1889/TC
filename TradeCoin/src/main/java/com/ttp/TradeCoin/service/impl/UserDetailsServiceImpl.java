/**
 * 
 */
package com.ttp.TradeCoin.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttp.TradeCoin.dao.UserDao;
import com.ttp.TradeCoin.entity.Role;
import com.ttp.TradeCoin.entity.User;

/**
 * @author phattt
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		
		// get user by userId
		User user = userDao.findUserByUserId(userId);
		
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		
		// set granted authority
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		
		// set role to ganted authority
		for (Role role : user.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(), grantedAuthorities);
	}
}
