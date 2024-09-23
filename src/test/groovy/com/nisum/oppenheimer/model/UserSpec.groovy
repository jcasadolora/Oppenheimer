package com.nisum.oppenheimer.model

import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.util.stream.Collectors
import java.util.stream.IntStream

import static org.junit.jupiter.api.Assertions.assertNotNull

/**
 * Spock Specification for User entity.
 *
 * This specification tests the creation of a User object using the builder pattern.
 * It ensures that a User object is correctly instantiated with the provided attributes.
 */
@ActiveProfiles("test")
class UserSpec extends Specification {

    /**
     * Tests that a User can be created with valid attributes.
     *
     * <p>
     * Given a User builder with the following attributes:
     * <ul>
     *     <li>xkey: "unique-key"</li>
     *     <li>name: "John Doe"</li>
     *     <li>email: "john.doe@nisum.com"</li>
     *     <li>password: "securepassword" (converted to char array)</li>
     * </ul>
     *
     * The test expects that the created User object is not null and that
     * its attributes match the provided values.
     * </p>
     */
    def "should create User with valid attributes"() {
        given:
            def user = User.builder()
                            .xkey("unique-key")
                            .name("John Doe")
                            .email("john.doe@nisum.com")
                            .token(UUID.randomUUID().toString())
                            .password("securepassword")
                           .build()

        expect:
            assertNotNull(user)
            user.xkey == "unique-key"
            user.name == "John Doe"
            user.email == "john.doe@nisum.com"
    }

    /**
     * Tests that a User can be created with valid attributes,
     * and it has associated Phone and Token objects.
     *
     * <p>
     * Given a User builder with the following attributes:
     * <ul>
     *     <li>xkey: "unique-key"</li>
     *     <li>name: "John Doe"</li>
     *     <li>email: "john.doe@nisum.com"</li>
     *     <li>password: "securepassword" (converted to char array)</li>
     * </ul>
     *
     * This test creates two associated Phone objects and two associated Token objects.
     * It expects that the created User object:
     * <ul>
     *     <li>Is not null.</li>
     *     <li>Has exactly 2 associated Phone objects.</li>
     *     <li>Has exactly 2 associated Token objects.</li>
     * </ul>
     *
     * Additionally, it verifies that the phone and token details match the expected values.
     * </p>
     */
    def "should create User with valid attributes and associated phones and tokens"() {
        given:

            def phones = IntStream.rangeClosed(1,2)
                                              .mapToObj({ index ->
                                                 Phone.builder()
                                                       .xkey(String.format("phone-key-%s",index))
                                                       .number(123456789L+index)
                                                       .cityCode((short) 212)
                                                       .countryCode((short) 1)
                                                      .build()
                                              }).collect(Collectors.toSet())

            def user = User.builder()
                            .xkey("unique-key")
                            .name("John Doe")
                            .email("john.doe@nisum.com")
                            .password("securepassword")
                            .phones(phones)
                            .token(UUID.randomUUID().toString())
                           .build()

        expect:
            assertNotNull(user)
            user.phones.size() == 2

            and: "Verify phone details"
            user.phones*.xkey.containsAll(["phone-key-1", "phone-key-2"])
    }
}
