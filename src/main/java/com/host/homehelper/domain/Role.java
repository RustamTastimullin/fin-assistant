package com.host.homehelper.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Rustam Tastimullin (Rustam.Tastimullin@lanit-tercom.com) created on 12.01.2023.
 */
public enum Role implements GrantedAuthority {

	USER((short) 1, "USER"),
	USER_VIP((short) 2, "USER_VIP"),
	ADMIN((short) 3, "ADMIN");
	private final Short id;
	private final String name;

	Role(Short id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return name();
	}
}
