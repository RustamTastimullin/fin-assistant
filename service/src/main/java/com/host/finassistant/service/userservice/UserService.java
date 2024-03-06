package com.host.finassistant.service.userservice;

import com.host.finassistant.entity.User;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * Сервис управления сущностью User.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 03-Sep-23
 */
public interface UserService {

	/**
	 * Поиск пользователя по ИД.
	 *
	 * @param userId ИД пользователя.
	 * @return найденый пользователь.
	 */
	@NotNull
	Optional<User> findUserById(@NotNull Long userId);

	/**
	 * Поиск пользователя по e-mail.
	 *
	 * @param email e-mail пользователя.
	 * @return найденный пользователь.
	 */
	@NotNull
	Optional<User> findUserByEmail(@NotNull String email);

	/**
	 * Удаление пользователя по ИД.
	 *
	 * @param userId ИД пользователя.
	 */
	void deleteById(@NotNull Long userId);

	/**
	 * Поиск всех пользователей в БД.
	 *
	 * @return список всех пользователей.
	 */
	@NotNull
	List<User> findAllUsers();

	/**
	 * Сохранение передаваемого пользователя.
	 *
	 * @param user сохраняемый пользователь.
	 * @return сохраненный пользователь.
	 */
	@NotNull
	User saveUser(@NotNull User user);

}
