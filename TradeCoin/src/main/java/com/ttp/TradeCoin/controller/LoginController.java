/**
 * 
 */
package com.ttp.TradeCoin.controller;

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
		
		return "home";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model, String error) {
		
		// check error
		if (error != null && !"".equals(error)) {
			model.addAttribute("errorLogin", "user pass not match!");
		}
		
		return "login";
	}
}
