package com.host.simplehelper.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * Промежуточная таблица между User и Role.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 11.04.2023.
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "users_roles")
public class UserRoles {

	@NotNull
	@EmbeddedId
	private UserRolesKey key;

	@ToString.Exclude
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id", nullable = false)
	@MapsId("roleId")
	private Role roles;

	public UserRoles() {
		this.key = new UserRolesKey();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof UserRoles userRoles)) {
			return false;
		}
		return Objects.equals(getKey(), userRoles.getKey());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getKey());
	}

}
