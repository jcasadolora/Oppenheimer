package com.nisum.oppenheimer.service.spec;

import com.nisum.oppenheimer.api.restful.controllers.dto.UserDTO;
import com.nisum.oppenheimer.service.record.UserRecord;
import jakarta.validation.constraints.NotNull;

/**
 * UserService is an interface that defines the contract for user-related operations.
 * It provides methods to manage user data within the application.
 */
public interface UserService {
    /**
     * Adds a new user based on the provided UserDTO.
     *
     * @param dto the UserDTO object containing user details; must not be null.
     * @return a UserRecord representing the added user, including generated fields
     *         such as id, created, modified timestamps, and token.
     * @throws IllegalArgumentException if the provided user is null.
     */
    UserRecord create(@NotNull UserDTO dto);
}
