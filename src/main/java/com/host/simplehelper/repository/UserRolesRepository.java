package com.host.simplehelper.repository;

import com.host.simplehelper.domain.UserRoles;
import com.host.simplehelper.domain.UserRolesKey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий промежуточной таблицы между User и Roles.
 *
 * @author Rustam Tastimullin (tastimullin@mail.ru) created on 12.04.2023.
 */
public interface UserRolesRepository extends JpaRepository<UserRoles, UserRolesKey> {

}
