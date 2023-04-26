package com.host.homehelper.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Роли, их обозначения и разрешения.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 12.01.2023.
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

	@Id
	@Column(name = "id", nullable = false, unique = true, updatable = false)
	private Long id;

	@Column(name = "name", nullable = false, unique = true, updatable = false, length = 20)
	private String name;

	@Column(name = "authorities", nullable = false, updatable = false)
	private String authorities;

	@Getter(AccessLevel.NONE)
	@ToString.Exclude
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	@Cascade({CascadeType.SAVE_UPDATE})
	private Set<User> users = new HashSet<>();

	public Role(Long id, String name, String authorities) {
		this.id = id;
		this.name = name;
		this.authorities = authorities;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Role role)) {
			return false;
		}
		return getId().equals(role.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
