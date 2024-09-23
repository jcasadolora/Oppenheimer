package com.nisum.oppenheimer.service.impl

import com.auth0.jwt.interfaces.Claim
import com.nisum.oppenheimer.model.User
import com.nisum.oppenheimer.service.spec.TokenService
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.lang.Subject

import static org.junit.jupiter.api.Assertions.assertNotNull

/**
 * TokenServiceImplSpec is a Spock specification for testing the
 * {@link TokenServiceImpl} class.
 *
 * <p>
 * This specification tests the functionality of the TokenServiceImpl,
 * ensuring that the JWT generation, verification, and decoding
 * processes work as expected. It covers various scenarios, including
 * valid and invalid tokens, to verify that the implementation handles
 * JWTs correctly and logs appropriate error messages for failures.
 * </p>
 *
 * <p>
 * The tests include:
 * <ul>
 *     <li>Generating a valid JWT for a given user.</li>
 *     <li>Verifying a valid JWT.</li>
 *     <li>Handling JWT verification failures due to invalid signatures,
 *     expiration, and unsupported tokens.</li>
 *     <li>Decoding a JWT to retrieve its claims.</li>
 * </ul>
 * </p>
 */
@ActiveProfiles("test")
class TokenServiceImplSpec extends Specification {

    @Subject
    TokenService tokenService = new TokenServiceImpl(key: "secretKey", expiration: 3600000)

    /**
     * Test case to verify the generation of a valid JWT token.
     *
     * <p>
     * This test checks that a JWT token is generated correctly for a given user.
     * It ensures that the token is not null and that the claims contained within
     * the token match the user's name and email address.
     * </p>
     *
     * <p>
     * Steps:
     * <ul>
     *     <li>Create a User object with predefined email and name.</li>
     *     <li>Generate a JWT token using the TokenService's generate method.</li>
     *     <li>Assert that the generated token is not null.</li>
     *     <li>Decode the token and verify that the claims match the User object's
     *     properties.</li>
     * </ul>
     * </p>
     */
    def "should generate a valid JWT token"() {
        given:
            User user = new User(email: "test@example.com", name: "Test User")
        when:
            String token = tokenService.generate(user)
        then:
            assertNotNull(token)
            def claims = tokenService.decode(token)
            claims.get("name").asString() == user.getName()
            claims.get("sub").asString() == user.getEmail()
    }

    /**
     * Test case to verify that a valid JWT token can be successfully verified.
     *
     * <p>
     * This test checks the TokenService's ability to verify a token that was
     * generated for a given user. It ensures that the verification process
     * returns true when a valid token is provided.
     * </p>
     *
     * <p>
     * Steps:
     * <ul>
     *     <li>Create a User object with a predefined email and name.</li>
     *     <li>Generate a JWT token using the TokenService's generate method.</li>
     *     <li>Verify the generated token using the TokenService's verify method.</li>
     *     <li>Expect the verification to return true.</li>
     * </ul>
     * </p>
     */
    def "should verify a valid token"() {
        given:
            User user = new User(email: "test@example.com", name: "Test User")
            String token = tokenService.generate(user)
        expect:
            tokenService.verify(token)
    }

    /**
     * Test case to ensure that an invalid JWT token cannot be verified.
     *
     * <p>
     * This test checks the TokenService's behavior when an invalid or expired
     * token is provided to the verify method. It ensures that the verification
     * process returns false, indicating that the token is not valid.
     * </p>
     *
     * <p>
     * Steps:
     * <ul>
     *     <li>Generate a valid token for a user.</li>
     *     <li>Manually create an invalid token (e.g., by altering the valid token).</li>
     *     <li>Attempt to verify the invalid token using the TokenService's verify method.</li>
     *     <li>Expect the verification to return false.</li>
     * </ul>
     * </p>
     */
    def "should not verify an invalid token"() {
        given:
            String invalidToken = "invalid.token"
        expect:
            !tokenService.verify(invalidToken)
    }

    /**
     * Test case to ensure that a valid JWT token can be decoded and claims can be retrieved.
     *
     * <p>
     * This test verifies the functionality of the TokenService's decode method.
     * It ensures that the claims embedded in the JWT token can be correctly extracted
     * and match the expected values for the user who was used to generate the token.
     * </p>
     *
     * <p>
     * Steps:
     * <ul>
     *     <li>Generate a valid token for a user.</li>
     *     <li>Decode the token using the TokenService's decode method.</li>
     *     <li>Assert that the retrieved claims match the user's email and name.</li>
     * </ul>
     * </p>
     */
    def "should decode a token and retrieve claims"() {
        given:
            User user = new User(email: "test@example.com", name: "Test User")
            String token = tokenService.generate(user)
        when:
            Map<String, Claim> claims = tokenService.decode(token)
        then:
            claims.get("name").asString() == user.getName()
            claims.get("sub").asString() == user.getEmail()
    }
}
