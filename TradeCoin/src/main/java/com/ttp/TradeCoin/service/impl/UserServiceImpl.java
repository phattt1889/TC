/**
 * 
 */
package com.ttp.TradeCoin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ttp.TradeCoin.dao.UserDao;
import com.ttp.TradeCoin.entity.User;
import com.ttp.TradeCoin.service.UserService;

/**
 * @author phattt
 *
 */
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public void save(User user) {

		// save user
		userDao.save(user);
	}

	@Override
	public User findUserByUserId(String userId) {
		
		return userDao.findUserByUserId(userId);
	}

}
