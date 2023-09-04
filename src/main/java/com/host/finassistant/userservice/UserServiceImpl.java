package com.host.finassistant.userservice;

import com.host.finassistant.domain.entity.User;
import com.host.finassistant.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис управления сущностью User.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 03-Sep-23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository repository;

	@NotNull
	@Override
	public Optional<User> findUserByEmail(@NotNull String email) {
		return repository.findByEmail(email);
	}

	@Override
	public void deleteById(@NotNull Long userId) {
		repository.deleteById(userId);
	}

	@NotNull
	@Override
	public List<User> findAllUsers() {
		return repository.findAll();
	}

	@NotNull
	@Override
	public User saveUser(@NotNull User user) {
		return repository.save(user);
	}

	@NotNull
	@Override
	public Optional<User> findUserById(@NotNull Long userId) {
		return repository.findById(userId);
	}
}