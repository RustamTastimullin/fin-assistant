package com.host.simplehelper.userservice;

import com.host.simplehelper.domain.Role;
import com.host.simplehelper.domain.User;

import java.util.List;
import java.util.Map;

/**
 * Пользовательский сервис.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 13.01.2023.
 */
public interface UserService {

	/**
	 * Создание пользователя (по дефолту с правами USER).
	 *
	 * @param user пользователь.
	 * @return пользователь успешно создан.
	 */
	boolean createUser(User user);

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
	User findByEmail(String email);

	/**
	 * Список всех пользователей.
	 *
	 * @return все пользователи из бд.
	 */
	List<User> findAllUsers();

	/**
	 * Список всех ролей в системе.
	 *
	 * @return список ролей из бд.
	 */
	List<Role> getRolesList();

}
