package com.host.homehelper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Rustam Tastimullin (Rustam.Tastimullin@lanit-tercom.com) created on 16.01.2023.
 */
@Controller
@PreAuthorize("hasAnyAuthority('ADMIN')")
@EnableMethodSecurity
@RequestMapping("/secure")
@RequiredArgsConstructor
public class SecureController {

	@GetMapping
	public String adminTmp() {
		return "secure/admin-page";
	}

}
