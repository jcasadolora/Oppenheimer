package com.nisum.oppenheimer.service.impl

import com.nisum.oppenheimer.api.restful.controllers.dto.PhoneDTO
import com.nisum.oppenheimer.api.restful.controllers.dto.UserDTO
import com.nisum.oppenheimer.model.Phone
import com.nisum.oppenheimer.model.User
import com.nisum.oppenheimer.repository.UserRepository
import com.nisum.oppenheimer.service.record.UserRecord
import com.nisum.oppenheimer.service.spec.TokenService
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Subject

import static org.junit.jupiter.api.Assertions.assertNotNull

/**
 * Spock Specification for auditing functionality in entities that extend Auditable.
 *
 * This specification tests the automatic setting of created and modified timestamps
 * for User, Token, and Phone entities upon persistence.
 */
@DataJpaTest
@ActiveProfiles("test")
class UserServiceImplSpec extends Specification {

    UserRepository userRepository = Mock(UserRepository)
    Argon2PasswordEncoder passwordEncoder = Mock(Argon2PasswordEncoder)
    TokenService tokenService = Mock(TokenService)

    @Subject
    UserServiceImpl userService = new UserServiceImpl(userRepository,passwordEncoder,tokenService)

    def "should create user successfully"() {
        given:
            UserDTO userDTO = UserDTO.builder()
                    .name("John Doe")
                    .email("john.doe@example.com")
                    .password("SecureP@ssw0rd")
                    .phones(List.of(new PhoneDTO("8093433232", "1", "57")))
                    .build()

            User savedUser = User.builder()
                                .xkey(UUID.randomUUID().toString())
                                .name(userDTO.getName())
                                .email(userDTO.getEmail())
                                .password(userDTO.getPassword())
                                .token(UUID.randomUUID().toString())
                                .phones(Set.of(Phone.builder()
                                                .xkey(UUID.randomUUID().toString())
                                                .number(Long.parseLong("8093433232"))
                                                .cityCode(Short.parseShort("1"))
                                                .countryCode(Short.parseShort("57"))
                                                .build()))
                                .build()

        when:
            UserRecord result = userService.create(userDTO)
        then:
            1 * userRepository.existsByEmail(userDTO.getEmail()) >> false
            1 * userRepository.save(_) >> savedUser
            assertNotNull(result)
            result.id == savedUser.getXkey()
            result.token == savedUser.getToken()
            result.isActive()
    }

    @Ignore
    def "should create a new user successfully"() {
        given: "A valid UserDTO with no existing email in the database"
            def userDTO = new UserDTO(
                    name: "John Doe",
                    email: "john.doe@example.com",
                    password: "P@ssw0rd",
                    phones: [
                        new PhoneDTO("1234567890", "01", "1")
                    ]
            )
        and: "A user entity to be saved"
            def savedUser = User.builder()
                    .xkey(UUID.randomUUID().toString())
                    .name(userDTO.name)
                    .email(userDTO.email)
                    .password(userDTO.password)
                    .phones([
                            Phone.builder()
                                    .xkey(UUID.randomUUID().toString())
                                    .number(Long.parseLong("1234567890"))
                                    .cityCode(Short.parseShort("01"))
                                    .countryCode(Short.parseShort("1"))
                                    .build()
                    ] as Set)
                    .token(UUID.randomUUID().toString())
                    .build()

        when: "The create method is called"
            1 * userRepository.existsByEmail(userDTO.email) >> false
            1 * userRepository.save(_) >> savedUser

            def result = userService.create(userDTO)

        then: "The user is saved and a UserRecord is returned"
            result instanceof UserRecord
            result.id == savedUser.id
            result.created != null
            result.token == savedUser.token
            1 * userRepository.existsByEmail(userDTO.email)
            1 * userRepository.save(_)
    }

    @Ignore
    def "should throw IllegalArgumentException when email already exists"() {
        given: "A UserDTO with an email that already exists"
            def userDTO = new UserDTO(
                    name: "Jane Doe",
                    email: "jane.doe@example.com",
                    password: "P@ssw0rd",
                    phones: []
            )
        when: "The create method is called"
            userRepository.existsByEmail(userDTO.email) >> true
            userService.create(userDTO)
        then: "An exception is thrown"
            thrown(IllegalArgumentException)
            1 * userRepository.existsByEmail(userDTO.email)
            0 * userRepository.save(_)
    }
}
