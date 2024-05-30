package com.hrpirme.groupchatapp.service;

import com.hrpirme.groupchatapp.model.User;
import com.hrpirme.groupchatapp.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing user-related operations.
 */
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructs a new UserService with the provided UserRepository.
     *
     * @param userRepository The repository for accessing user data.
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Saves a new user with the specified username if it doesn't already exist.
     *
     * @param username The username of the user to save.
     */
    public void saveUser(String username) {
        if (!userRepository.findByUsername(username).isPresent()) {
            log.info("Save user by username: {}", username);
            User newUser = new User();
            newUser.setUsername(username);
            userRepository.save(newUser);
        }
    }


    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to find.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     */
    public Optional<User> findByUsername(String username) {
        log.info("Finding user by username: {}", username);
        return userRepository.findByUsername(username);
    }

    /**
     * Retrieves all users.
     *
     * @return A list containing all users.
     */
    public List<User> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll();
    }
}
