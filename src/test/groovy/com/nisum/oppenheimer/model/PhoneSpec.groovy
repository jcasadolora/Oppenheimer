package com.nisum.oppenheimer.model

import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static org.junit.jupiter.api.Assertions.assertNotNull

/**
 * Spock Specification for Phone entity.
 *
 * This specification tests the creation of a Phone object
 * with valid attributes and its association with a User object.
 */
@ActiveProfiles("test")
class PhoneSpec extends Specification {

    /**
     * Tests that a Phone can be created with valid attributes,
     * including its association with a User.
     *
     * <p>
     * Given a User object with the following attributes:
     * <ul>
     *     <li>id: 1L</li>
     *     <li>email: "john.doe@nisum.com"</li>
     * </ul>
     *
     * This test creates a Phone object with the following attributes:
     * <ul>
     *     <li>xkey: "phone-key"</li>
     *     <li>number: 1234567890L</li>
     *     <li>cityCode: (short) 212</li>
     *     <li>countryCode: (short) 1</li>
     *     <li>user: the User object created above</li>
     * </ul>
     *
     * The test expects that the created Phone object:
     * <ul>
     *     <li>Is not null.</li>
     *     <li>Has the correct xkey value.</li>
     *     <li>Has the correct number value.</li>
     *     <li>Has the correct city code.</li>
     *     <li>Has the correct country code.</li>
     *     <li>Is associated with the expected User object (email match).</li>
     * </ul>
     * </p>
     */
    def "should create Phone with valid attributes"() {
        given:
            def user = new User(id: 1L, email: "john.doe@nisum.com")
            def phone = Phone.builder()
                              .xkey("phone-key")
                              .number(1234567890L)
                              .cityCode((short) 212)
                              .countryCode((short) 1)
                              .user(user)
                             .build()

        expect:
            assertNotNull(phone)
            phone.xkey == "phone-key"
            phone.number == 1234567890L
            phone.cityCode == 212
            phone.countryCode == 1
            phone.user.email == "john.doe@nisum.com"
    }
}
