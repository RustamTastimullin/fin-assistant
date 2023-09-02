package com.host.finassistant.repository;

import com.host.finassistant.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий таблицы пользователей.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 13.01.2023.
 */
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	/**
	 * Ищем пользователей без роли 666.
	 *
	 * @return список пользователей без роли 666.
	 */
	@Query("select u from User u where u.id " +
			"not in (select ur.key.userId from UserRoles ur where ur.key.roleId = (:banList))")
	List<User> findUsersNotInRoleList(@Param("banList") List<Long> banList);
}
