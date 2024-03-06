package com.host.finassistant.endpoint;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер администратора.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 16.01.2023.
 */
@Controller
@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERUSER')")
@EnableMethodSecurity
@RequestMapping("/secure")
@RequiredArgsConstructor
public class AdminController {

	@GetMapping
	public String adminPage() {
		return "secure/admin-page";
	}

}
