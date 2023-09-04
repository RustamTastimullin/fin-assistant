package com.host.finassistant.userservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Сервис загрузки пользователя, реализация secure интерфейса UserDetailsService.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 13.01.2023.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserManagementService userManagementService;

	/**
	 * Загрузка пользователя по e-mail.
	 *
	 * @param email the username identifying the user whose data is required.
	 * @return пользователь, если найден
	 * @throws UsernameNotFoundException ошибка "Пользователь не найден"
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		var user = userManagementService.findUserByEmail(email);
		if (user.isPresent()) {
			return user.get();
		} else {
			log.info("Пользователь c e-mail={} не найден", email);
			throw new UsernameNotFoundException("Invalid email or password.");
		}
	}
}