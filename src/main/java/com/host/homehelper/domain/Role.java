package com.host.homehelper.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 12.01.2023.
 */
public enum Role implements GrantedAuthority {

	USER(1L, "USER"),
	SUPERUSER(777L, "SUPERUSER"),
	USER_1(3L, "USER_1"),
	USER_2(4L, "USER_2"),
	ADMIN(666L, "ADMIN");
	private final Long id;
	private final String name;

	Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return name();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static List<String> getAllRoles() {
		return Arrays.stream(Role.values()).map(Role::getName).collect(Collectors.toList());
	}
}
