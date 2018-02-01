package com.ttp.TradeCoin.service;

import org.springframework.stereotype.Component;

import com.ttp.TradeCoin.entity.User;

public interface UserService {

	public void save(User user);
	
	public User findUserByUserId(String userId);
}
