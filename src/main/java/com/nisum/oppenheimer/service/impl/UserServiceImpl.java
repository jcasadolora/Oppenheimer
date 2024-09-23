package com.nisum.oppenheimer.service.impl;

import com.nisum.oppenheimer.api.restful.controllers.dto.UserDTO;
import com.nisum.oppenheimer.model.Phone;
import com.nisum.oppenheimer.model.User;
import com.nisum.oppenheimer.repository.UserRepository;
import com.nisum.oppenheimer.service.record.UserRecord;
import com.nisum.oppenheimer.service.spec.TokenService;
import com.nisum.oppenheimer.service.spec.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link UserService} interface.
 * <p>
 * This service handles the business logic for user management, including the creation of users and
 * the management of associated phone numbers.
 * </p>
 *
 * <p>
 * It integrates with a {@link UserRepository} to perform database operations related to users,
 * such as saving new users or checking if a user already exists by email.
 * </p>
 *
 * <p>
 * The class is annotated with {@link Service} to indicate that it's a Spring-managed service,
 * and uses {@link RequiredArgsConstructor} from Lombok to automatically inject its dependencies.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final Argon2PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    /**
     * Creates a new user in the system.
     * <p>
     * This method first checks if a user with the same email already exists in the database. If the email
     * is unique, it proceeds to map the provided {@link UserDTO} to a {@link User} entity, including
     * mapping the associated phone numbers, and then saves the entity in the database.
     * </p>
     *
     * @param dto the {@link UserDTO} containing user details
     * @return a {@link UserRecord} containing the persisted user's details
     * @throws IllegalArgumentException if a user with the given email already exists
     */
    @Override
    public UserRecord create(UserDTO dto) {
        // Check if the user already exists by email
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Map DTO to Entity (Phones and User)
        var user = mapToUserEntity(dto);

        // Save the User entity
        user = userRepository.save(user);

        // Return UserRecord after successful creation
        return mapToUserRecord(user);
    }

    /**
     * Maps the {@link UserDTO} to a {@link User} entity.
     * <p>
     * This method handles the conversion of the user data transfer object to the corresponding
     * user entity for persistence. It generates UUIDs for the user and their associated phone numbers,
     * and sets the created and modified timestamps to the current time.
     * </p>
     *
     * @param dto the {@link UserDTO} containing user details
     * @return the mapped {@link User} entity
     */
    private User mapToUserEntity(UserDTO dto) {
        Set<Phone> phones = dto.getPhones().stream()
                .map(phoneDTO -> Phone.builder()
                        .xkey(UUID.randomUUID().toString())
                        .number(Long.parseLong(phoneDTO.getNumber()))
                        .cityCode(Short.parseShort(phoneDTO.getCityCode()))
                        .countryCode(Short.parseShort(phoneDTO.getCountryCode()))
                        .build())
                .collect(Collectors.toSet());

        var user = User.builder()
                        .xkey(UUID.randomUUID().toString())
                        .name(dto.getName())
                        .email(dto.getEmail())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .phones(phones)
                    .build();
        user.setToken(tokenService.generate(user));

        return user;
    }

    /**
     * Maps a {@link User} entity to a {@link UserRecord}.
     * <p>
     * This method is used to convert the saved user entity into a UserRecord, which will be
     * returned to the client. It includes the user's creation and modification timestamps
     * as well as their token and active status.
     * </p>
     *
     * @param user the {@link User} entity to be mapped
     * @return a {@link UserRecord} containing user details
     */
    private UserRecord mapToUserRecord(User user) {
        if(user.getCreated() == null) {
            user.setCreated(LocalDateTime.now());
        }
        if(user.getModified() == null) {
            user.setModified(LocalDateTime.now());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        return new UserRecord(
                user.getXkey(),
                user.getCreated().format(formatter),
                user.getModified().format(formatter),
                user.getCreated().format(formatter),
                user.getToken(),
                true  // Assuming new users are active by default
        );
    }
}
