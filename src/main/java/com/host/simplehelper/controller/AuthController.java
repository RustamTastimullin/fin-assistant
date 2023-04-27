package com.host.simplehelper.controller;

import com.host.simplehelper.domain.User;
import com.host.simplehelper.userservice.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 13.01.2023.
 */
@Controller
@EnableMethodSecurity
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;

	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}

	@GetMapping("/registration")
	public String registration() {
		return "auth/registration";
	}

	@PostMapping("/registration")
	@Transactional
	public String createUser(@Valid @ModelAttribute("user") User user,
							 Model model) {

		if (!userService.createUser(user)) {
			model.addAttribute("msg", "User with email: " + user.getEmail() + " already exist.");
			return "auth/registration";
		}
		return "auth/register-success";
	}


}
