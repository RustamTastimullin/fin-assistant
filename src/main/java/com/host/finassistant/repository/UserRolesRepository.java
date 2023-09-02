package com.host.finassistant.repository;

import com.host.finassistant.domain.entity.UserRoles;
import com.host.finassistant.domain.entity.UserRolesKey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий промежуточной таблицы между User и Roles.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 12.04.2023.
 */
public interface UserRolesRepository extends JpaRepository<UserRoles, UserRolesKey> {

}
