package com.nisum.oppenheimer.api.restful.controllers.dto

import com.nisum.oppenheimer.util.Constants
import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import jakarta.validation.Validator
import jakarta.validation.ValidatorFactory
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

/**
 * PhoneDTOSpec is a Spock specification that tests the validation constraints
 * of the PhoneDTO class. This class is annotated with @ActiveProfiles("test"),
 * indicating that it runs in the "test" profile, allowing for configuration
 * specific to testing environments.
 *
 * <p>
 * The tests within this specification validate the fields of the PhoneDTO class
 * to ensure that they meet the defined constraints. The validation checks include
 * the presence of required fields, format compliance for patterns, and length limits.
 * </p>
 *
 * <p>
 * Each test method follows the given-when-then structure, where:
 * <ul>
 *     <li>Given: Sets up the context or the input data for the test.</li>
 *     <li>When: Executes the action or behavior being tested.</li>
 *     <li>Then: Asserts the expected outcomes or states.</li>
 * </ul>
 * </p>
 */
@ActiveProfiles("test")
class PhoneDTOSpec extends Specification {

    private Validator validator

    def setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
        validator = factory.getValidator()
    }

    /**
     * Test to validate PhoneDTO with valid attributes.
     *
     * <p>
     * This test checks that a PhoneDTO instance with valid number, city code, and country code
     * passes validation without any constraint violations. It ensures that the validation logic
     * correctly identifies valid data.
     * </p>
     */
    def "should validate PhoneDTO with valid attributes"() {
        given:
            def phoneDTO = new PhoneDTO(
                    number: "8093433232",
                    cityCode: "1",
                    countryCode: "57"
            )
        when:
            Set<ConstraintViolation<PhoneDTO>> violations = validator.validate(phoneDTO)
        then:
            violations.isEmpty()
    }

    /**
     * Test to ensure PhoneDTO does not validate with a blank number.
     *
     * <p>
     * This test checks that a PhoneDTO instance with a blank number fails validation.
     * It verifies that the appropriate constraint violation is triggered, ensuring
     * that the validation logic enforces the requirement for a non-blank phone number.
     * </p>
     */
    def "should not validate PhoneDTO with blank number"() {
        given:
            def phoneDTO = new PhoneDTO(
                    number: "",
                    cityCode: "1",
                    countryCode: "57"
            )
        when:
            Set<ConstraintViolation<PhoneDTO>> violations = validator.validate(phoneDTO)
        then:
            assertEquals(2, violations.size())
            assertTrue(violations.any { it.message == Constants.PHONE_NUMBER_REQUIRED })
    }

    /**
     * Test to ensure PhoneDTO does not validate with an invalid city code.
     *
     * <p>
     * This test checks that a PhoneDTO instance with an invalid city code fails validation.
     * It verifies that the appropriate constraint violation is triggered, ensuring
     * that the validation logic enforces the requirement for a valid city code.
     * </p>
     */
    def "should not validate PhoneDTO with invalid city code"() {
        given:
            def phoneDTO = new PhoneDTO(
                    number: "8093433232",
                    cityCode: "invalid",
                    countryCode: "57"
            )
        when:
            Set<ConstraintViolation<PhoneDTO>> violations = validator.validate(phoneDTO)
        then:
            assertEquals(1, violations.size())
            assertTrue(violations.any { it.message == Constants.CITY_CODE_INVALID })
    }

    /**
     * Test to ensure PhoneDTO does not validate with an invalid city code.
     *
     * <p>
     * This test checks that a PhoneDTO instance with an invalid city code fails validation.
     * It verifies that the appropriate constraint violation is triggered, ensuring
     * that the validation logic enforces the requirement for a valid city code.
     * </p>
     */
    def "should not validate PhoneDTO with blank country code"() {
        given:
            def phoneDTO = new PhoneDTO(
                    number: "8093433232",
                    cityCode: "1",
                    countryCode: ""
            )
        when:
            Set<ConstraintViolation<PhoneDTO>> violations = validator.validate(phoneDTO)
        then:
            assertEquals(2, violations.size())
            assertTrue(violations.any { it.message == Constants.COUNTRY_CODE_REQUIRED })
    }

    /**
     * Test that verify the PhoneDTO does not validate
     * when the phone number exceeds the maximum allowed size.
     *
     * <p>
     * This test checks that:
     * <ul>
     *     <li>The validation process returns a violation when the
     *     'number' field exceeds the maximum length defined in
     *     {@code Constants.PHONE_NUMBER_MAX_SIZE}.</li>
     *     <li>Only one violation is expected, and it should match the
     *     message defined in {@code Constants.PHONE_NUMBER_INVALID_SIZE}.</li>
     * </ul>
     * </p>
     */
    def "should not validate PhoneDTO with number exceeding max size"() {
        given:
            def phoneDTO = new PhoneDTO(
                    number: "80934332321234512345",
                    cityCode: "1",
                    countryCode: "57"
            )
        when:
            Set<ConstraintViolation<PhoneDTO>> violations = validator.validate(phoneDTO)
        then:
            assertEquals(1, violations.size())
            assertTrue(violations.any { it.message == Constants.PHONE_NUMBER_INVALID_SIZE})
    }
}
