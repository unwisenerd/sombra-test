package com.sombra.project.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Authentication authentication) {
		if (authentication != null) {
			return "redirect:/";
		}
		return "login";
	}

}
