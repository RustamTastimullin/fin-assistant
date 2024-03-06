package com.host.finassistant.repository;

import com.host.finassistant.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий таблицы ролей.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 29.01.2023.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

}
