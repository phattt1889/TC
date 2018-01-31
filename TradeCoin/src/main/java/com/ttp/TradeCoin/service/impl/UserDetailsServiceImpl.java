/**
 * 
 */
package com.ttp.TradeCoin.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttp.TradeCoin.dao.RoleDao;
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
	
	@Autowired
	private RoleDao roleDao;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		
		// get user by userId
		User user = userDao.findUserByUserId(userId);
		
		// get all role
		List<Role> roles = roleDao.findAll();
		
		// set granted authority
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		
		// set role to ganted authority
		for (Role role : roles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(), grantedAuthorities);
	}
}
