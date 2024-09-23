package com.nisum.oppenheimer.service.record;

/**
 * UserRecord represents a user entity with relevant attributes.
 *
 * @param id          Unique identifier for the user.
 * @param created     Timestamp of when the user was created.
 * @param modified    Timestamp of when the user was last modified.
 * @param lastLogin   Timestamp of the user's last login.
 * @param token       Authentication token for the user.
 * @param isActive    Status indicating if the user is active.
 */
public record UserRecord(String id,
                         String created,
                         String modified,
                         String lastLogin,
                         String token, boolean isActive) {
}
