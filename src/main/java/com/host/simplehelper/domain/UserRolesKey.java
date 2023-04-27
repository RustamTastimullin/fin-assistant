package com.host.simplehelper.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * Композитный ключ для таблицы users_roles, между User и Role.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 12.04.2023.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRolesKey implements Serializable {

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "role_id", nullable = false)
	private Long roleId;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof UserRolesKey that)) {
			return false;
		}
		return Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getRoleId(), that.getRoleId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUserId(), getRoleId());
	}
}
