package com.host.finassistant.service.userservice;

import com.host.finassistant.entity.Role;
import com.host.finassistant.entity.User;
import com.host.finassistant.repository.RoleRepository;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис управления пользователем.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 02.09.2023.
 */
@Slf4j
@Service
public class UserManagementServiceImpl implements UserManagementService {

	public static final String OPTION_ENABLED = "on";

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	private final List<Role> rolesList;

	public UserManagementServiceImpl(UserService userService,
									 PasswordEncoder passwordEncoder,
									 RoleRepository roleRepository) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
		this.rolesList = findAllRoles();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean createUser(@NotNull User user) {

		var founded = findUserByEmail(user.getEmail());
		if (founded.isPresent()) {
			log.info("Пользователь уже существует: {}", founded);
			return false;
		}

		user.setActive(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		// роли сеттятся вручную, по дефолту создается юзер с правами "USER"
		var roleUser = getRolesList().stream()
				.filter(role -> role.getName().equals("USER") && role.getId().equals(10L))
				.findFirst().orElseThrow();
		user.getRoles().add(roleUser);

		userService.saveUser(user);
		log.info("Пользователь успешно создан");
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void editUser(User user, Map<String, String> form) {

		var rolesNameList = getRolesList().stream()
				.map(Role::getName)
				.toList();
		var userRoles = user.getRoles();
		var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		var isAdmin = authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch(role -> role.equals("ADMIN"));
		var isSuperuser = authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch(r -> r.equals("SUPERUSER"));

		if (isAdmin || isSuperuser) {
			userRoles.clear();
		}

		var formKeySet = form.keySet();
		for (String key : formKeySet) {
			switch (key) {
				case "firstname" -> {
					String thisValue = form.get(key);
					if (StringUtils.isNotBlank(thisValue)) {
						user.setFirstName(thisValue);
					}
				}
				case "email" -> {
					String thisValue = form.get(key);
					if (StringUtils.isNotBlank(thisValue)) {
						user.setEmail(thisValue);
					}
				}
				case "lastname" -> {
					String thisValue = form.get(key);
					user.setLastName(thisValue);
				}
				case "phonenumber" -> {
					String thisValue = form.get(key);
					user.setPhoneNumber(thisValue);
				}
				case "password" -> {
					String thisValue = form.get(key);
					if (StringUtils.isNotBlank(thisValue)) {
						user.setPassword(passwordEncoder.encode(thisValue));
					}
				}
				// ROLES
				default -> {
					if (key.equals("ADMIN") && !isAdmin) {
						continue;
					}
					if (isAdmin || isSuperuser) {
						if (rolesNameList.contains(key)) {
							var thisValue = form.get(key);
							if (OPTION_ENABLED.equalsIgnoreCase(thisValue)) {
								var newRole = getRolesList().stream()
										.filter(role -> role.getName().equals(key))
										.findFirst()
										.orElse(null);
								userRoles.add(newRole);
							}
						}
					}
				}
			}
		}

		userService.saveUser(user);
		log.info("Данные пользователя успешно сохранены");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteUser(Long userId) {

		if (userService.findUserById(userId).isPresent()) {
			try {
				userService.deleteById(userId);
				log.info("Пользователь={} успешно удален", userId);
			} catch (Exception e) {
				throw new RuntimeException("Can't delete user with id=" + userId, e.getCause());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@NotNull
	@Override
	public Optional<User> findUserByEmail(@NotNull String email) {
		return userService.findUserByEmail(email);
	}

	/**
	 * {@inheritDoc}
	 */
	@NotNull
	@Override
	public List<User> findAllUsers() {
		return userService.findAllUsers();
	}

	/**
	 * {@inheritDoc}
	 */
	@NotNull
	@Override
	public List<Role> getRolesList() {
		return rolesList;
	}

	/**
	 * Загрузка ролей при старте приложения.
	 *
	 * @return все роли, найденные в БД.
	 */
	@NotNull
	private List<Role> findAllRoles() {
		return roleRepository.findAll();
	}

}