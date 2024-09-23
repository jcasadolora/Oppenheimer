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
 * Spock Specification for testing the UserDTO class.
 *
 * <p>
 * This specification is annotated with {@code @ActiveProfiles("test")}, indicating that
 * it will run under the "test" profile. This allows for configuration tailored specifically
 * for testing, such as mock beans or different application properties.
 * </p>
 *
 * <p>
 * The tests within this class verify the validation and serialization behavior of the
 * {@code UserDTO} class, ensuring that all constraints defined in the DTO are correctly
 * enforced and that the JSON representation aligns with expected formats.
 * </p>
 *
 * <p>
 * The specification may include tests for scenarios such as:
 * <ul>
 *     <li>Validating that all required fields are present and valid.</li>
 *     <li>Checking the correct handling of invalid data.</li>
 *     <li>Testing serialization to and deserialization from JSON.</li>
 * </ul>
 * </p>
 */
@ActiveProfiles("test")
class UserDTOSpec extends Specification {

    private Validator validator

    def setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
        validator = factory.getValidator()
    }

    /**
     * Tests the validation of the UserDTO with valid attributes.
     *
     * <p>
     * Given a UserDTO object with the following attributes:
     * <ul>
     *     <li><strong>name</strong>: "John Doe" (valid name)</li>
     *     <li><strong>email</strong>: "john.doe@example.com" (valid email format)</li>
     *     <li><strong>password</strong>: "@Password123" (valid password meeting complexity requirements)</li>
     *     <li><strong>phones</strong>: [] (an empty list of phones, which is optional)</li>
     * </ul>
     * The test expects that the UserDTO object is valid and that there are no constraint violations.
     * </p>
     *
     * <p>
     * The test utilizes a validator to check the constraints defined in the UserDTO class.
     * If there are no violations, the validation is considered successful.
     * </p>
     */
    def "should validate UserDTO with valid attributes"() {
        given:
            def userDTO = new UserDTO(
                    name: "John Doe",
                    email: "john.doe@example.com",
                    password: "@Password123",
                    phones: []
            )
        when:
            Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO)
        then:
            violations.isEmpty()
    }

    /**
     * Tests that the UserDTO is not valid when the name field is blank.
     *
     * <p>
     * Given a UserDTO object with the following attributes:
     * <ul>
     *     <li><strong>name</strong>: "" (blank name, which is invalid)</li>
     *     <li><strong>email</strong>: "john.doe@example.com" (valid email format)</li>
     *     <li><strong>password</strong>: "@Password123" (valid password meeting complexity requirements)</li>
     *     <li><strong>phones</strong>: [] (an empty list of phones, which is optional)</li>
     * </ul>
     * The test expects that the UserDTO object has validation violations and that
     * the violation message for the name field matches the required message defined in {@code Constants}.
     * </p>
     *
     * <p>
     * The test utilizes a validator to check the constraints defined in the UserDTO class.
     * It asserts that there is exactly one violation and that the violation message is as expected.
     * </p>
     */
    def "should not validate UserDTO with blank name"() {
        given:
            def userDTO = new UserDTO(
                    name: "",
                    email: "john.doe@example.com",
                    password: "@Password123",
                    phones: []
            )
        when:
            Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO)
        then:
            assertEquals(1, violations.size())
            assertTrue(violations.any { it.message == Constants.NAME_REQUIRED })
    }

    /**
     * Tests that the UserDTO is not valid when the email field contains an invalid format.
     *
     * <p>
     * Given a UserDTO object with the following attributes:
     * <ul>
     *     <li><strong>name</strong>: "John Doe" (valid name)</li>
     *     <li><strong>email</strong>: "invalid-email" (invalid email format)</li>
     *     <li><strong>password</strong>: "@Password123" (valid password meeting complexity requirements)</li>
     *     <li><strong>phones</strong>: [] (an empty list of phones, which is optional)</li>
     * </ul>
     * The test expects that the UserDTO object has validation violations and that
     * the violation message for the email field matches the invalid message defined in {@code Constants}.
     * </p>
     *
     * <p>
     * The test utilizes a validator to check the constraints defined in the UserDTO class.
     * It asserts that there are exactly two violations and that one of the violation messages is as expected.
     * </p>
     */
    def "should not validate UserDTO with invalid email"() {
        given:
            def userDTO = new UserDTO(
                    name: "John Doe",
                    email: "invalid-email",
                    password: "@Password123",
                    phones: []
            )
        when:
            Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO)
        then:
            assertEquals(2, violations.size())
            assertTrue(violations.any { it.message == Constants.EMAIL_INVALID })
    }

    /**
     * Tests that the UserDTO is not valid when the password does not meet strength requirements.
     *
     * <p>
     * Given a UserDTO object with the following attributes:
     * <ul>
     *     <li><strong>name</strong>: "John Doe" (valid name)</li>
     *     <li><strong>email</strong>: "john.doe@example.com" (valid email)</li>
     *     <li><strong>password</strong>: "password" (weak password that does not meet complexity requirements)</li>
     *     <li><strong>phones</strong>: [] (an empty list of phones, which is optional)</li>
     * </ul>
     * The test expects that the UserDTO object has a validation violation and that
     * the violation message for the password field matches the weak password message defined in {@code Constants}.
     * </p>
     *
     * <p>
     * The test utilizes a validator to check the constraints defined in the UserDTO class.
     * It asserts that there is exactly one violation and that the violation message corresponds to the expected weak password message.
     * </p>
     */
    def "should not validate UserDTO with weak password"() {
        given:
            def userDTO = new UserDTO(
                    name: "John Doe",
                    email: "john.doe@example.com",
                    password: "password",
                    phones: []
            )
        when:
            Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO)
        then:
            assertEquals(1, violations.size())
            assertTrue(violations.any { it.message == Constants.PASSWORD_WEAK })
    }

    /**
     * Tests that the UserDTO is not valid when the phone number does not conform to PhoneDTO rules.
     *
     * <p>
     * Given a UserDTO object with the following attributes:
     * <ul>
     *     <li><strong>name</strong>: "John Doe" (valid name)</li>
     *     <li><strong>email</strong>: "john.doe@example.com" (valid email)</li>
     *     <li><strong>password</strong>: "@Password123" (valid password)</li>
     *     <li><strong>phones</strong>: A list containing a PhoneDTO object with an invalid number (empty string).</li>
     * </ul>
     * </p>
     *
     * <p>
     * The test expects that the UserDTO object has two validation violations:
     * one for the phone number being required and one for the phone number format.
     * The violation messages should match the corresponding messages defined in {@code Constants}.
     * </p>
     *
     * <p>
     * The test utilizes a validator to check the constraints defined in the UserDTO class,
     * ensuring that the phone number validation logic is enforced correctly.
     * </p>
     */
    def "should not validate UserDTO with phone number not conforming to PhoneDTO rules"() {
        given:
            def invalidPhoneDTO = new PhoneDTO(number: "", cityCode: "1", countryCode: "57")
            def userDTO = new UserDTO(
                    name: "John Doe",
                    email: "john.doe@example.com",
                    password: "@Password123",
                    phones: [invalidPhoneDTO]
            )
        when:
            Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO)
        then:
            assertEquals(2, violations.size())
            assertTrue(violations.any { it.message == Constants.PHONE_NUMBER_REQUIRED })
    }
}
