package com.hrpirme.groupchatapp.repository;

import com.hrpirme.groupchatapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing user data in the database.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to find.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     */
    Optional<User> findByUsername(String username);
}
