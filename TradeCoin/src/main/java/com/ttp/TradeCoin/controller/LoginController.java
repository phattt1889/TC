/**
 * 
 */
package com.ttp.TradeCoin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ttp.TradeCoin.Enums.StockExchangeEnums;
import com.ttp.TradeCoin.Factory.TradeFactory;
import com.ttp.TradeCoin.entity.User;
import com.ttp.TradeCoin.service.SecurityService;
import com.ttp.TradeCoin.service.TradeApiService;
import com.ttp.TradeCoin.service.UserService;

/**
 * @author phattt
 *
 */
@Controller
public class LoginController {
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value={"/", "/home"}, method = RequestMethod.GET)
	public String home(Model model) {
		
		String userId = securityService.findLoggedInUsername();
		// get name user
		model.addAttribute("username", securityService.findLoggedInUsername());
		
		// get info user login
		User user = userService.findUserByUserId(userId);
		
		// init factory trade
		TradeFactory tf = new TradeFactory(StockExchangeEnums.POLONIEX);
		TradeApiService poloniexStock = tf.getCurrentStockExchange(user);
		
		Object result = poloniexStock.getDepositAddresses();
		
		System.out.println(result.toString());
		
		return "home";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model, String error) {
		model.addAttribute("test", "abc");
		// check error
		if (error != null) {
			model.addAttribute("errorLogin", "user pass not match!");
		}
		
		return "login";
	}
}
