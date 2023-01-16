package com.host.homehelper.controller;

import com.host.homehelper.domain.Role;
import com.host.homehelper.domain.User;
import com.host.homehelper.userservice.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
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
 * @author Rustam Tastimullin (Rustam.Tastimullin@lanit-tercom.com) created on 16.01.2023.
 */
@Controller
@RequestMapping("/profile")
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@GetMapping("all")
	public String findAllRegisteredUsers(Model model) {
		model.addAttribute("users", userService.findAllUsers());
		return "user-service/users-list";
	}

	@PreAuthorize("hasAnyAuthority('ADMIN') or principal.id == #user.id")
	@GetMapping(value = "{user}")
	public String userEditForm(@PathVariable User user, Model model) {
		model.addAttribute("user", user);
		var isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Role.ADMIN);
		model.addAttribute("userIsAdmin", isAdmin);
		model.addAttribute("roles", Role.values());
		return "user-service/user-edit";
	}

	@PreAuthorize("hasAnyAuthority('ADMIN') or principal.id == #user.id")
	@PostMapping("{user}")
	@Transactional(rollbackOn = Exception.class)
	public String editUser(
			@RequestParam Map<String, String> form,
			@RequestParam("userId") User user) {

		var isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Role.ADMIN);
		userService.editUser(user, form, isAdmin);
		return "redirect:/about";
	}

	@PreAuthorize("hasAnyAuthority('ADMIN') or principal.id == #user.id")
	@DeleteMapping(value = "{user}/delete")
	@Transactional(rollbackOn = Exception.class)
	public String deleteUser(
			@RequestParam("userId") User user
	) {

		userService.deleteUser(user.getId());
		return "redirect:/about";
	}

}
