package com.host.finassistant.service.userservice;

import com.host.finassistant.entity.Role;
import com.host.finassistant.entity.User;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис управления пользователем.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 13.01.2023.
 */
public interface UserManagementService {

	/**
	 * Создание пользователя (по дефолту с правами USER).
	 *
	 * @param user пользователь.
	 * @return пользователь успешно создан.
	 */
	boolean createUser(@NotNull User user);

	/**
	 * Редактирование пользователя.
	 *
	 * @param user пользователь.
	 * @param form входные данные для парсинга в пользователя.
	 */
	void editUser(User user, Map<String, String> form);

	/**
	 * Удаление пользователя.
	 *
	 * @param userId ид пользователя.
	 */
	void deleteUser(Long userId);

	/**
	 * Поиск пользователя по email.
	 *
	 * @param email параметр email.
	 * @return найденный в бд пользователь.
	 */
	@NotNull
	Optional<User> findUserByEmail(String email);

	/**
	 * Список всех пользователей.
	 *
	 * @return все пользователи из бд.
	 */
	@NotNull
	List<User> findAllUsers();

	/**
	 * Список всех ролей в системе.
	 *
	 * @return список ролей из бд.
	 */
	@NotNull
	List<Role> getRolesList();

}