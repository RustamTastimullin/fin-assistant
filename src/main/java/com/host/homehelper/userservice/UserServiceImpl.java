package com.host.homehelper.userservice;

import com.host.homehelper.domain.Role;
import com.host.homehelper.domain.User;
import com.host.homehelper.repository.RoleRepository;
import com.host.homehelper.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Пользовательский сервис.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 13.01.2023.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	private static List<Role> rolesList;

	@PostConstruct
	public void init() {
		rolesList = roleRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean createUser(User user) {

		if (findByEmail(user.getEmail()) != null) {
			return false;
		}

		user.setActive(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		// по дефолту создается юзер с правами "USER"
		var roleUser = rolesList.stream()
				.filter(role -> role.getId().equals(10L))
				.findFirst().orElseThrow();
		user.getRoles().add(roleUser);
		userRepository.save(user);

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void editUser(User user, Map<String, String> form) {
		var rolesNameList = rolesList.stream()
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
							if ("on".equalsIgnoreCase(thisValue)) {
								var newRole = rolesList.stream()
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

		userRepository.save(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteUser(Long userId) {

		if (userRepository.findById(userId).isPresent()) {
			try {
				userRepository.deleteById(userId);
			} catch (Exception e) {
				throw new RuntimeException("Can't delete user with id= " + userId, e.getCause());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> findAllUsers() {

		return userRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> getRolesList() {
		return rolesList;
	}


	/**
	 * Security block
	 */

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = findByEmail(email);
		if (user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Invalid email or password.");
		}
	}
}