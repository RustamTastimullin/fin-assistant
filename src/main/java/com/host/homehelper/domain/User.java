package com.host.homehelper.domain;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.Set;

/**
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 13.01.2023.
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "USERS")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false, unique = true)
	private Long id;

	@Nonnull
	@Column(name = "EMAIL", nullable = false, unique = true, length = 45)
	@NotEmpty(message = "Email should not be empty")
	private String email;

	@Nonnull
	@Column(name = "USER_NAME", nullable = false, length = 20)
	private String username;

	@Column(name = "LAST_NAME", length = 20)
	private String lastName;

	@Nonnull
	@Column(name = "PASSWORD", nullable = false, length = 64)
	@NotEmpty(message = "Password should not be empty")
	private String password;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	@Column(name = "IS_ACTIVE")
	private boolean active;

	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "USERS_ROLE", joinColumns = @JoinColumn(name = "USERS_ID"))
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;

	/**
	 * Security block
	 */
	@Override
	public Set<Role> getAuthorities() {
		return getRoles();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isActive();
	}

	/**
	 * Equals / hashCode / toString
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User user)) {
			return false;
		}
		return getId().equals(user.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", email='" + email + '\'' +
				", username='" + username + '\'' +
				", lastName='" + lastName + '\'' +
				", password='" + password + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", active=" + active +
				", roles=" + roles +
				'}';
	}
}