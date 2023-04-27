package com.host.simplehelper.repository;

import com.host.simplehelper.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий ролей.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 29.01.2023.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);

}
