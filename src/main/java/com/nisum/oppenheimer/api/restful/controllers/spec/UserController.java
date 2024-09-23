package com.nisum.oppenheimer.api.restful.controllers.spec;

import com.nisum.oppenheimer.api.restful.controllers.dto.UserDTO;
import com.nisum.oppenheimer.service.record.UserRecord;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

/**
 * UserController Interface
 *
 * This interface defines the contract for user-related operations in the application.
 * It includes methods for user sign-up functionality.
 *
 * <p>Implementations of this interface are responsible for handling HTTP requests
 * related to user operations, specifically for signing up new users.</p>
 */
public interface UserController {

    /**
     * Signs up a new user with the provided UserDTO.
     *
     * @param userDTO the Data Transfer Object containing user details required for sign-up.
     * @return a ResponseEntity containing the created UserRecord and the corresponding HTTP status.
     *         If the sign-up is successful, the status will be 201 Created.
     *         If there are validation errors, an appropriate error response will be returned.
     */
    ResponseEntity<UserRecord> signUp(UserDTO userDTO);
}
