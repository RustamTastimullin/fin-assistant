package com.host.finassistant.endpoint;

import com.host.finassistant.entity.Role;
import com.host.finassistant.entity.User;
import com.host.finassistant.service.userservice.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Контроллер управления пользователем.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 16.01.2023.
 */
@Controller
@RequestMapping("/profile")
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class UserController {

	private final UserManagementService userManagementService;

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERUSER')")
	@GetMapping("all")
	public String findAllRegisteredUsers(Model model) {
		model.addAttribute("users", userManagementService.findAllUsers());
		return "user-service/users-list";
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERUSER') or principal.id == #user.id")
	@GetMapping(value = "{user}")
	public String userEditForm(@PathVariable User user, Model model) {
		var rolesNameList = userManagementService.getRolesList().stream().map(Role::getName).toList();
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
		userManagementService.editUser(user, form);
		return "redirect:/about";
	}

	@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERUSER') or principal.id == #user.id")
	@DeleteMapping(value = "{user}/delete")
	@Transactional
	public String deleteUser(
			@RequestParam("userId") User user
	) {
		userManagementService.deleteUser(user.getId());
		return "redirect:/logout";
	}

}
