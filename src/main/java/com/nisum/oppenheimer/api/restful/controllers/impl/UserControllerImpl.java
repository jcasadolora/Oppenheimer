package com.nisum.oppenheimer.api.restful.controllers.impl;

import com.nisum.oppenheimer.api.restful.controllers.dto.UserDTO;
import com.nisum.oppenheimer.api.restful.controllers.spec.UserController;
import com.nisum.oppenheimer.service.record.UserRecord;
import com.nisum.oppenheimer.service.spec.UserService;
import com.nisum.oppenheimer.util.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Implementation of the {@link UserController} interface, handling user-related RESTful requests.
 * <p>
 * This controller provides endpoints for signing up new users using the {@link UserDTO}. The sign-up
 * process includes validating the request, creating the user in the system, and responding with the
 * appropriate HTTP status code and headers.
 * </p>
 * <p>
 * The class uses {@link RequiredArgsConstructor} to inject dependencies and uses {@link UserService}
 * for handling business logic.
 * </p>
 */
@RestController
@RequestMapping(
        value = Constants.USER_PATH_REST_ENDPOINT,
        consumes = Constants.USER_V1_MEDIA_TYPE,
        produces = Constants.USER_V1_MEDIA_TYPE
)
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

    private final UserService userService;

    /**
     * Handles the HTTP POST request for signing up a new user.
     * <p>
     * The method validates the incoming {@link UserDTO} and processes the sign-up by invoking the
     * {@link UserService#create} method. Upon successful creation, the newly created user's information
     * is returned in the response body along with the location header of the created resource.
     * </p>
     *
     * @param dto the {@link UserDTO} containing user details for sign-up
     * @return a {@link ResponseEntity} containing the created {@link UserRecord} and appropriate HTTP headers
     */
    @Override
    @PostMapping
    public ResponseEntity<UserRecord> signUp(@Valid @RequestBody UserDTO dto) {
        logger.info("Processing sign-up request for email: {}", dto.getEmail());

        // Create the user via the service layer
        var rspBody = this.userService.create(dto);

        // Log creation success
        logger.info("Successfully created user with ID: {}", rspBody.id());

        // Build the URI for the created resource
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                  .path("/{id}")
                                                  .buildAndExpand(rspBody.id())
                                                  .toUri();
        // Add necessary headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", Constants.USER_V1_MEDIA_TYPE);
        httpHeaders.add("Location", location.toString());

        // Return a 201 Created response with the location header
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(rspBody);
    }
}
