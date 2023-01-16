package com.host.homehelper.controller;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Rustam Tastimullin (Rustam.Tastimullin@lanit-tercom.com) created on 13.01.2023.
 */
@Controller
@RequestMapping("/")
@EnableMethodSecurity
public class MainController {

	@GetMapping(value = {"/about", "*"})
	public String about(Model model) {
		model.addAttribute("title", "About");
		return "about";
	}

}
