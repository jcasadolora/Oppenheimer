package com.nisum.oppenheimer.model

import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static org.junit.jupiter.api.Assertions.assertNotNull

/**
 * Spock Specification for Token entity.
 *
 * This specification tests the creation of a Token object
 * with valid attributes and its association with a User object.
 */
@ActiveProfiles("test")
class TokenSpec extends Specification {

    /**
     * Tests that a Token can be created with valid attributes,
     * including its association with a User.
     *
     * <p>
     * Given a User object with the following attributes:
     * <ul>
     *     <li>id: 1L</li>
     *     <li>email: "john.doe@nisum.com"</li>
     * </ul>
     *
     * This test creates a Token object with the following attributes:
     * <ul>
     *     <li>xkey: "token-key"</li>
     *     <li>jwt: "jwt-token"</li>
     *     <li>status: Token.TokenStatus.ENABLE</li>
     *     <li>user: the User object created above</li>
     * </ul>
     *
     * The test expects that the created Token object:
     * <ul>
     *     <li>Is not null.</li>
     *     <li>Has the correct xkey value.</li>
     *     <li>Has the correct jwt value.</li>
     *     <li>Has the correct status.</li>
     *     <li>Is associated with the expected User object (email match).</li>
     * </ul>
     * </p>
     */
    def "should create Token with valid attributes"() {
        given:
            def user = new User(id: 1L, email: "john.doe@nisum.com")
            def token = Token.builder()
                              .xkey("token-key")
                              .jwt("jwt-token")
                              .status(Token.TokenStatus.ENABLE)
                              .user(user)
                             .build()

        expect:
            assertNotNull(token)
            token.xkey == "token-key"
            token.jwt == "jwt-token"
            token.status == Token.TokenStatus.ENABLE
            token.user.email == "john.doe@nisum.com"
    }
}
