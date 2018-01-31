package com.ttp.TradeCoin.service;

public interface SecurityService {

	public String findLoggedInUsername();

	public void autologin(String userId, String password);
}
