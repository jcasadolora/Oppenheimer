package com.nisum.oppenheimer.repository;

import com.nisum.oppenheimer.model.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing User entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations and
 * additional query methods for the User entity.
 * </p>
 *
 * @see User
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>  {

    /**
     * Checks if a User with the specified email exists in the database.
     *
     * @param email the email of the User to check for
     * @return true if a User with the given email exists, false otherwise
     */
    Boolean existsByEmail(@NotNull String email);

    /**
     * Retrieves a User by their email address.
     *
     * @param email the email of the User to retrieve
     * @return an Optional containing the User if found, or empty if not found
     */
    Optional<User> findByEmail(@NotNull String email);
}