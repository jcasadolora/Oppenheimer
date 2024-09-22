package com.nisum.oppenheimer.repository

import com.nisum.oppenheimer.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static org.junit.jupiter.api.Assertions.assertNotNull

/**
 * Spock Specification for the UserRepository.
 * This specification tests the repository methods for saving,
 * checking existence by email, and retrieving users.
 */
@DataJpaTest
@ActiveProfiles("test")
class UserRepositorySpec extends Specification {

    @Autowired
    UserRepository userRepository

    /**
     * Tests the saving of a User and checks for existence by email.
     *
     * <p>
     * Given a User object with the following attributes:
     * <ul>
     *     <li>xkey: "unique-key"</li>
     *     <li>name: "John Doe"</li>
     *     <li>email: "john.doe@nisum.com"</li>
     *     <li>password: "securepassword" (converted to char array)</li>
     * </ul>
     *
     * The test performs the following actions:
     * <ol>
     *     <li>Saves the User object to the repository.</li>
     * </ol>
     *
     * The test expects that:
     * <ul>
     *     <li>Checking the existence of the email "john.doe@nisum.com" returns <code>true</code>.</li>
     *     <li>Checking the existence of the email "nonexistent@nisum.com" returns <code>false</code>.</li>
     * </ul>
     * </p>
     */
    def "should save a user and check existence by email"() {
        given:
            def user = User.builder()
                            .xkey("unique-key")
                            .name("John Doe")
                            .email("john.doe@nisum.com")
                            .password("securepassword".toCharArray())
                           .build()
        when:
            userRepository.save(user as User)
        then:
            userRepository.existsByEmail("john.doe@nisum.com") == true
            userRepository.existsByEmail("nonexistent@nisum.com") == false
    }

    /**
     * Tests the retrieval of a User by email.
     *
     * <p>
     * Given a User object with the following attributes:
     * <ul>
     *     <li>xkey: "unique-key"</li>
     *     <li>name: "John Doe"</li>
     *     <li>email: "john.doe@nisum.com"</li>
     *     <li>password: "securepassword" (converted to char array)</li>
     * </ul>
     *
     * The test performs the following actions:
     * <ol>
     *     <li>Saves the User object to the repository.</li>
     * </ol>
     *
     * The test expects that:
     * <ul>
     *     <li>Retrieving the User by the email "john.doe@nisum.com" returns an <code>Optional</code> that is present.</li>
     *     <li>The retrieved User's name matches "John Doe".</li>
     * </ul>
     * </p>
     */
    def "should retrieve a user by email"() {
        given:
            def user = User.builder()
                            .xkey("unique-key")
                            .name("John Doe")
                            .email("john.doe@nisum.com")
                            .password("securepassword".toCharArray())
                           .build()
            userRepository.save(user)
        when:
            def retrievedUser = userRepository.findByEmail("john.doe@nisum.com")
        then:
            assertNotNull(retrievedUser)
            retrievedUser.isPresent()
            retrievedUser.get().name == "John Doe"
    }

    /**
     * Tests that retrieving a non-existent User by email returns an empty result.
     *
     * <p>
     * This test does not require any setup, as it directly attempts to find a User
     * using an email address that is not present in the repository:
     * <ul>
     *     <li>Email: "nonexistent@nisum.com"</li>
     * </ul>
     *
     * The test expects that:
     * <ul>
     *     <li>The retrieved User object is not null.</li>
     *     <li>The retrieved User's <code>Optional</code> is not present (i.e., it indicates that no User was found).</li>
     * </ul>
     * </p>
     */
    def "should return empty when retrieving a non-existent user by email"() {
        when:
            def retrievedUser = userRepository.findByEmail("nonexistent@nisum.com")
        then:
            assertNotNull(retrievedUser)
            !retrievedUser.isPresent()
    }
}
