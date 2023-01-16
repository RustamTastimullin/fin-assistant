package com.host.homehelper.repository;

import com.host.homehelper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Rustam Tastimullin (Rustam.Tastimullin@lanit-tercom.com) created on 13.01.2023.
 */
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String userName);

	Optional<User> findByEmailOrUsername(String email, String userName);
}
