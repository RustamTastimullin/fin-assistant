package com.host.finassistant.repository;

import com.host.finassistant.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий таблицы ролей.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 29.01.2023.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);

}
