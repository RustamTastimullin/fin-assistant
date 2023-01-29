package com.host.homehelper.controller;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 13.01.2023.
 */
@Controller
@RequestMapping("/service")
@EnableMethodSecurity
public class ServiceController {

	@GetMapping
	public String service() {
		return "service/service";
	}
}
