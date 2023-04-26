package com.host.homehelper.repository;

import com.host.homehelper.domain.UserRoles;
import com.host.homehelper.domain.UserRolesKey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий промежуточной таблицы между User и Roles.
 *
 * @author Rustam Tastimullin (Rustam.Tastimullin@lanit-tercom.com) created on 12.04.2023.
 */
public interface UserRolesRepository extends JpaRepository<UserRoles, UserRolesKey> {

}
