package com.nisum.oppenheimer.model

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static org.junit.jupiter.api.Assertions.assertNotNull

/**
 * Spock Specification for auditing functionality in entities that extend Auditable.
 *
 * This specification tests the automatic setting of created and modified timestamps
 * for User, Token, and Phone entities upon persistence.
 */
@DataJpaTest
@ActiveProfiles("test")
class AuditableSpec extends Specification {

    @PersistenceContext
    private EntityManager entityManager

    /**
     * Tests that the created and modified timestamps are correctly set
     * when a User entity is persisted.
     *
     * <p>
     * Given a User object with valid attributes:
     * <ul>
     *     <li>xkey: "user1"</li>
     *     <li>name: "John Doe"</li>
     *     <li>email: "john@nisum.com"</li>
     *     <li>password: "password" (converted to char array)</li>
     * </ul>
     *
     * When the User is persisted, the test expects that:
     * <ul>
     *     <li>The created timestamp is not null.</li>
     *     <li>The modified timestamp is not null.</li>
     *     <li>The created timestamp is before the modified timestamp.</li>
     * </ul>
     * </p>
     */
    @Transactional
    def "should set created and modified timestamps on persist for User"() {
        given:
            def user = new User(xkey: "user1",
                                name: "John Doe",
                                email: "john@nisum.com",
                                token: UUID.randomUUID().toString(),
                                password: "password")
        when:
            entityManager.persist(user)
            entityManager.flush()

        then:
            assertNotNull(user)
            user.created != null
            user.modified != null
            user.created.isBefore(user.modified)
    }

    /**
     * Tests that the created and modified timestamps are correctly set
     * when a Phone entity is persisted.
     *
     * <p>
     * Given a User object and a Phone object with valid attributes:
     * <ul>
     *     <li>xkey: "phone1"</li>
     *     <li>number: 1234567890L</li>
     *     <li>cityCode: 1</li>
     *     <li>countryCode: 1</li>
     *     <li>user: the User object created above</li>
     * </ul>
     *
     * When the Phone is persisted, the test expects that:
     * <ul>
     *     <li>The created timestamp is not null.</li>
     *     <li>The modified timestamp is not null.</li>
     *     <li>The created timestamp is before the modified timestamp.</li>
     * </ul>
     * </p>
     */
    @Transactional
    def "should set created and modified timestamps on persist for Phone"() {
        given:
            def user = new User(xkey: "user1",
                    name: "John Doe",
                    email: "john@nisum.com",
                    token: UUID.randomUUID().toString(),
                    password: "password")
            entityManager.persist(user)

            def phone = new Phone(xkey: "phone1", number: 1234567890L, cityCode: 1, countryCode: 1, user: user)

        when:
            entityManager.persist(phone)
            entityManager.flush()

        then:
            assertNotNull(user)
            assertNotNull(phone)
            phone.created != null
            phone.modified != null
            phone.created.isBefore(phone.modified)
    }

    /**
     * Tests that the modified timestamp is set correctly when a User entity is updated.
     *
     * <p>
     * Given a User entity with the following attributes:
     * <ul>
     *     <li>xkey: "user1"</li>
     *     <li>name: "John Doe"</li>
     *     <li>email: "john@nisum.com"</li>
     *     <li>password: "password" (converted to char array)</li>
     * </ul>
     *
     * The test persists the user and captures the initial created timestamp before making any changes.
     *
     * When the user entity is modified (the name is changed to "Updated Name") and merged back into the
     * persistence context, the test expects that:
     * <ul>
     *     <li>The modified timestamp is not null.</li>
     *     <li>The modified timestamp is after the initial created timestamp.</li>
     * </ul>
     * </p>
     */
    def "should set modified timestamp on update for User"() {
        given:
        def user = new User(xkey: "user1",
                name: "John Doe",
                email: "john@nisum.com",
                token: UUID.randomUUID().toString(),
                password: "password")
            entityManager.persist(user)
            entityManager.flush()

            // Capture the created timestamp before the update
            def initialCreated = user.created

        when:
            user.name = "Updated Name" // Modify the entity
            entityManager.merge(user) // Trigger the update
            entityManager.flush() // Ensure the update occurs

        then:
            assertNotNull(user)
            user.modified != null
            user.modified.isAfter(initialCreated) // Ensure modified timestamp is after created
    }
}
