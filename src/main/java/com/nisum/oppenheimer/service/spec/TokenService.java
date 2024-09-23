package com.nisum.oppenheimer.service.spec;

import com.auth0.jwt.interfaces.Claim;
import com.nisum.oppenheimer.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

/**
 * TokenService defines the contract for generating, verifying, and decoding JWT tokens.
 * This service is responsible for managing user authentication tokens in the system.
 */
public interface TokenService {

    /**
     * Generates a JWT token for the given user.
     *
     * @param user The user for whom the token will be generated.
     * @return A string representation of the generated JWT token.
     */
    String generate(@NotNull User user);

    /**
     * Verifies the validity of the provided JWT token.
     *
     * @param token The JWT token to be verified.
     * @return true if the token is valid, false otherwise.
     */
    boolean verify(@Valid String token);

    /**
     * Decodes the provided JWT token and extracts claims.
     *
     * @param token The JWT token to be decoded.
     * @return A map of claims extracted from the token.
     */
    Map<String, Claim> decode(@Valid String token);
}
