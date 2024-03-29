package com.host.finassistant.endpoint;

import com.host.finassistant.entity.User;
import com.host.finassistant.service.userservice.UserManagementService;
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
 * Контроллер авторизации.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 13.01.2023.
 */
@Controller
@EnableMethodSecurity
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserManagementService userManagementService;

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

		if (!userManagementService.createUser(user)) {
			model.addAttribute("msg", "User with email: " + user.getEmail() + " already exist.");
			return "auth/registration";
		}
		return "auth/register-success";
	}


}
