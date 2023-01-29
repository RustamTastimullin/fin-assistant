package com.host.homehelper.userservice;

import com.host.homehelper.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 13.01.2023.
 */
public interface UserService {

	boolean createUser(User user);

	void editUser(User user, Map<String, String> form);

	void deleteUser(Long userId);

	User findByEmail(String email);

	List<User> findAllUsers();

}
