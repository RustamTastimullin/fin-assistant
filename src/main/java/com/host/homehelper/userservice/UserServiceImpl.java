package com.host.homehelper.userservice;

import com.host.homehelper.domain.Role;
import com.host.homehelper.domain.User;
import com.host.homehelper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 13.01.2023.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final List<String> allRoles = Role.getAllRoles();

	@Override
	public boolean createUser(User user) {

		if (findByEmail(user.getEmail()) != null) {
			return false;
		}

		user.setActive(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Collections.singleton(Role.USER));

		userRepository.save(user);
		return true;
	}

	@Override
	public void editUser(User user, Map<String, String> form) {

		Set<Role> roles = user.getRoles();
		var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		var isAuthorizedForRoleEdit = authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch(r -> r.equals(Role.ADMIN.getName()) || r.equals(Role.SUPERUSER.getName()));

		if (isAuthorizedForRoleEdit) {
			roles.clear();
		}

		Set<String> formKeySet = form.keySet();
		for (String key : formKeySet) {
			switch (key) {
				case "username" -> {
					String thisValue = form.get(key);
					if (StringUtils.isNotBlank(thisValue)) {
						user.setUsername(thisValue);
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
					if (isAuthorizedForRoleEdit) {
						if (allRoles.contains(key)) {
							String thisValue = form.get(key);
							if (thisValue.equalsIgnoreCase("on")) {
								roles.add(Role.valueOf(key));
							}
						}
					}
				}
			}
		}

		userRepository.save(user);
	}

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

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
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