package com.host.homehelper.controller;

import com.host.homehelper.domain.Role;
import com.host.homehelper.domain.User;
import com.host.homehelper.userservice.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 16.01.2023.
 */
@Controller
@RequestMapping("/profile")
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERUSER')")
	@GetMapping("all")
	public String findAllRegisteredUsers(Model model) {
		model.addAttribute("users", userService.findAllUsers());
		return "user-service/users-list";
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERUSER') or principal.id == #user.id")
	@GetMapping(value = "{user}")
	public String userEditForm(@PathVariable User user, Model model) {
		var rolesNameList = userService.getRolesList().stream().map(Role::getName).toList();
		var userRoles = user.getRoles().stream()
				.map(Role::getName)
				.toList();
		model.addAttribute("user", user);
		model.addAttribute("userRoles", userRoles);
		model.addAttribute("roles", rolesNameList);
		return "user-service/user-edit";
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERUSER') or principal.id == #user.id")
	@PostMapping("{user}")
	@Transactional
	public String editUser(
			@RequestParam Map<String, String> form,
			@RequestParam("userId") User user) {
		userService.editUser(user, form);
		return "redirect:/about";
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERUSER') or principal.id == #user.id")
	@DeleteMapping(value = "{user}/delete")
	@Transactional
	public String deleteUser(
			@RequestParam("userId") User user
	) {
		userService.deleteUser(user.getId());
		return "redirect:/logout";
	}

}
