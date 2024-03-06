package com.host.finassistant.repository;

import com.host.finassistant.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий таблицы пользователей.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 13.01.2023.
 */
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Поиск пользователя по e-mail.
	 *
	 * @param email e-mail пользователя
	 * @return пользователь.
	 */
	@NotNull
	Optional<User> findByEmail(@NotNull String email);

}
