/**
 * 
 */
package com.ttp.TradeCoin.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author phattt
 *
 */
@Controller
public class LoginController {

	@RequestMapping(value={"/", "/home"}, method = RequestMethod.GET)
	public String home(Model model) {
		
		// get user name authenticate
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		// get name user
		model.addAttribute("username", auth.getName());
		
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
