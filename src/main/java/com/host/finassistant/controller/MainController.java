package com.host.finassistant.controller;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Главный контроллер.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 13.01.2023.
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
