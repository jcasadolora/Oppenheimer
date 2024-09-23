package com.nisum.oppenheimer.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.nisum.oppenheimer.model.User;
import com.nisum.oppenheimer.service.spec.TokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * TokenServiceImpl is an implementation of the TokenService interface that handles
 * the generation, verification, and decoding of JSON Web Tokens (JWT) for user authentication.
 *
 * <p>
 * This service uses the Auth0 JWT library to create and validate tokens. The tokens are signed
 * using a secret key and have a configurable expiration time.
 * </p>
 *
 * <p>
 * The class contains the following functionalities:
 * <ul>
 *     <li>{@code generate(User user)}: Creates a JWT for the given user, including their email and name as claims.</li>
 *     <li>{@code verify(String token)}: Validates the provided JWT and checks its signature and expiration.</li>
 *     <li>{@code decode(String token)}: Extracts claims from the provided JWT without validating it.</li>
 * </ul>
 * </p>
 *
 * <p>
 * The class logs errors during token verification using SLF4J, providing insights into issues such as
 * invalid signatures or expired tokens.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Value("${token.key}")
    private String key;

    @Value("${token.expiration}")
    private Long expiration;

    /**
     * Generates a JSON Web Token (JWT) for the specified user.
     *
     * <p>
     * This method creates a JWT that includes the user's email as the subject and their name as a claim.
     * The token is signed with a secret key and includes information about the issuer, audience, and expiration time.
     * </p>
     *
     * @param user The user for whom the token is being generated. Must not be {@code null}.
     * @return A signed JWT as a {@code String} that can be used for user authentication.
     * @throws IllegalArgumentException if the user is {@code null} or if any user properties required for the token are missing.
     */
    @Override
    public String generate(User user) {
        // Calculate the expiration date for the token
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);

        // Create and sign the JWT
        return JWT.create()
                    .withSubject(user.getEmail())
                    .withClaim("name", user.getName())
                    .withIssuer("nisum")
                    .withAudience("test")
                    .withExpiresAt(expirationDate)
                  .sign(Algorithm.HMAC256(key));
    }

    /**
     * Verifies the validity of the provided JWT token.
     *
     * <p>
     * This method checks the signature, expiration, and overall validity of the token.
     * If the token is valid, it returns {@code true}; otherwise, it logs an error and returns {@code false}.
     * </p>
     *
     * @param token The JWT token to be verified. Must not be {@code null} or empty.
     * @return {@code true} if the token is valid; {@code false} otherwise.
     * @throws IllegalArgumentException if the token is {@code null} or empty.
     */
    @Override
    public boolean verify(String token) {
        // Check if the token is null or empty
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token must not be null or empty");
        }
        try {
            // Verify the token's signature and claims using the specified algorithm
            JWT.require(Algorithm.HMAC256(key)).build().verify(token);
            return true;    // Token is valid
        } catch (SignatureVerificationException e) {
            // Log an error if the signature is invalid
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (TokenExpiredException e) {
            // Log an error if the token is expired
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (JWTVerificationException e) {
            // Log an error for other verification failures
            logger.error("JWT token verification failed: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Decodes the given JWT token to extract its claims.
     *
     * <p>
     * This method does not validate the token; it simply retrieves the claims
     * contained within the token. The returned map can be used to access specific
     * claims like the subject, expiration, etc.
     * </p>
     *
     * @param token the JWT token to decode
     * @return a map containing the claims extracted from the token
     */
    @Override
    public Map<String, Claim> decode(String token) {
        // Decode the JWT token and retrieve the claims as a map
        return JWT.decode(token).getClaims();
    }
}
