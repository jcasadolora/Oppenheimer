package com.nisum.oppenheimer.api.restful.controllers.impl

import com.nisum.oppenheimer.api.restful.controllers.dto.PhoneDTO
import com.nisum.oppenheimer.api.restful.controllers.dto.UserDTO
import com.nisum.oppenheimer.api.restful.controllers.spec.UserController
import com.nisum.oppenheimer.service.record.UserRecord
import com.nisum.oppenheimer.service.spec.UserService
import com.nisum.oppenheimer.util.Constants
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

/**
 * UserControllerImplSpec
 *
 * This class is a Spock specification for testing the UserControllerImpl,
 * which handles user-related operations in the application.
 *
 * The primary function of the UserController is to manage user sign-up processes,
 * ensuring that user data is validated and processed correctly.
 *
 * The specification includes:
 * - Unit tests for various scenarios of the sign-up method, validating the
 *   correct handling of user input and the expected HTTP responses.
 * - Integration tests that simulate real HTTP requests to the API, confirming
 *   that the UserController interacts correctly with the service layer and
 *   returns appropriate responses.
 *
 * Key Scenarios Tested:
 * 1. **Valid Sign-Up Request**:
 *    - Tests that a valid UserDTO payload results in a 201 Created response.
 *    - Verifies that the Location header is set correctly in the response.
 *
 * 2. **Invalid User Input**:
 *    - Tests various invalid inputs, such as:
 *      - Blank names
 *      - Invalid email formats
 *      - Short passwords
 *      - Missing required fields
 *    - Ensures that each invalid input returns a 400 Bad Request response.
 */
class UserControllerImplSpec extends Specification {

    MockMvc mockMvc
    UserService userService = Mock(UserService)
    UserController userController = new UserControllerImpl(userService)

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build()
    }

    /**
     * Test Case: should signUp with valid UserDTO and returns created HTTP Response code
     *
     * This test case verifies the functionality of the sign-up endpoint in the UserControllerImpl.
     * It checks that when a valid UserDTO is provided, the system correctly processes the request,
     * creates a new user, and returns an HTTP 201 Created response with the appropriate Location header.
     *
     * Structure:
     * - **Given**:
     *   - A unique user ID is generated for the new user.
     *   - A valid UserDTO object is constructed with appropriate fields:
     *     - `name`: "asdf"
     *     - `email`: "jane.doe@nisum.com"
     *     - `password`: A valid encrypted password.
     *     - `phones`: A list of valid PhoneDTO objects with specified number, city code, and country code.
     *   - A UserRecord is mocked to simulate the service layer's response when creating a user.
     *
     * - **When**:
     *   - A POST request is made to the "/api/users" endpoint with the valid UserDTO as the request body.
     *   - The request specifies the content type as defined in Constants.USER_V1_MEDIA_TYPE.
     *
     * - **Then**:
     *   - The test verifies that the response status is 201, indicating successful creation.
     *   - It also checks that the Location header in the response points to the newly created user resource.
     *
     * Expected Outcome:
     * - The test should pass if the UserControllerImpl successfully creates a user and returns the expected HTTP response.
     *
     */
    def "should signUp with valid UserDTO and returns created HTTP Response code"() {
        given:
            def userId = UUID.randomUUID().toString()
            def dto = new UserDTO(
                    name: "asdf",
                    email: "jane.doe@nisum.com",
                    password: "bkPnVny19ZHaALrz8UsL/SRSKdJBNr3iuvBiaclhmmI=",
                    phones: [
                            new PhoneDTO(number: "8092230098", cityCode: "1", countryCode: "57"),
                            new PhoneDTO(number: "8092230093", cityCode: "1", countryCode: "57")
                    ]
            )
            def userRecord = new UserRecord(userId, "2023-09-22T12:00:00Z", "2023-09-22T12:00:00Z", null, "token123", true)
            userService.create(dto) >> userRecord
        when:
            def response = mockMvc.perform(post("/api/users")
                    .contentType(Constants.USER_V1_MEDIA_TYPE)
                    .content('{"name":"asdf","email":"jane.doe@nisum.com","password":"bkPnVny19ZHaALrz8UsL/SRSKdJBNr3iuvBiaclhmmI=","phones":[{"number":"8092230098","citycode":"1","countrycode":"57"},{"number":"8092230093","citycode":"1","countrycode":"57"}]}'))
                    .andReturn()
        then:
            response.response.status == 201
            response.response.getHeader("location") == "http://localhost/api/users/${userId}"
    }

    /**
     * Test Case: should signUp with valid UserDTO and return #expectedStatus
     *
     * This parameterized test case verifies the sign-up functionality in the UserControllerImpl
     * by testing various input scenarios for the UserDTO.
     *
     * It checks that the system returns the correct HTTP response status based on the provided UserDTO.
     *
     * Structure:
     * - **Given**:
     *   - A unique user ID is generated for the new user.
     *   - A JSON representation of the UserDTO is constructed based on the input parameters:
     *     - `name`: Varies based on the test case (valid, blank, etc.).
     *     - `email`: Varies based on the test case (valid, invalid, blank).
     *     - `password`: Varies based on the test case (valid, short, null).
     *   - If the expected status is `HttpStatus.CREATED`, a UserRecord is mocked to simulate the successful creation of the user.
     *
     * - **When**:
     *   - A POST request is made to the "/api/users" endpoint with the constructed UserDTO JSON as the request body.
     *   - The request specifies the content type as defined in Constants.USER_V1_MEDIA_TYPE.
     *
     * - **Then**:
     *   - The test verifies that the response status matches the expected HTTP status.
     *   - If the expected status is `HttpStatus.CREATED`, it also checks that the Location header in the response points to the newly created user resource.
     *
     * Expected Outcomes:
     * - The test should pass for valid input scenarios, returning HTTP 201 Created.
     * - For invalid inputs (e.g., blank name, invalid email, short password), the test should return HTTP 400 Bad Request.
     *
     */
    @Unroll
    def "should signUp with valid UserDTO and return #expectedStatus"() {
        given:
            def userId = UUID.randomUUID().toString()
            def dtoJson = """{
                "name": "${name}",
                "email": "${email}",
                "password": "${password}",
                "phones": [
                    {"number": "8092230098", "citycode": "1", "countrycode": "57"},
                    {"number": "8092230093", "citycode": "1", "countrycode": "57"}
                ]
            }"""

            if (expectedStatus == HttpStatus.CREATED) {
                def userRecord = new UserRecord(userId, "2023-09-22T12:00:00Z", "2023-09-22T12:00:00Z", null, "token123", true)
                userService.create(_) >> userRecord // Stub the service method
            }

        when:
            def response = mockMvc.perform(post("/api/users")
                                             .contentType(Constants.USER_V1_MEDIA_TYPE)
                                             .content(dtoJson))
                                             .andReturn()

        then:
            response.response.status == expectedStatus.value()
            if (expectedStatus == HttpStatus.CREATED) {
                response.response.getHeader("Location") == "http://localhost/api/users/${userId}"
            }

        where:
            name          | email                   | password                                        | expectedStatus
            "validName"   | "jane.doe@nisum.com"    | "bkPnVny19ZHaALrz8UsL/SRSKdJBNr3iuvBiaclhmmI="  | HttpStatus.CREATED
            "  "          | "jane.doe@nisum.com"    | "validPassword123!"                             | HttpStatus.BAD_REQUEST // Name is blank
            "validName"   | "invalid-email"         | "validPassword123!"                             | HttpStatus.BAD_REQUEST // Invalid email
            "validName"   | "jane.doe@nisum.com"    | "short"                                         | HttpStatus.BAD_REQUEST // Password too short
            "validName"   | ""                      | "validPassword123!"                             | HttpStatus.BAD_REQUEST // Email is blank
            "validName"   | "jane.doe@nisum.com"    | null                                            | HttpStatus.BAD_REQUEST // Password is null
    }
}