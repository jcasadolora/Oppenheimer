package com.nisum.oppenheimer.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a User entity in the application.
 *
 * This class is mapped to the "USERS" table in the database and extends the
 * {@link Auditable} class to include common auditing fields. It utilizes Lombok
 * annotations to reduce boilerplate code for getters, setters, and builders.
 *
 * <p>Key features include:</p>
 * <ul>
 *     <li>Unique email constraint to prevent duplicate users.</li>
 *     <li>Support for one-to-many relationships with Phone and Token entities.</li>
 *     <li>Serialization support with a defined {@code serialVersionUID}.</li>
 * </ul>
 *
 * <p>Note: The password is stored as a char array for security reasons.</p>
 *
 * @see Auditable
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Table(name="USERS")
@Access(AccessType.FIELD)
@ToString(exclude = {"phones","tokens"})
public class User extends Auditable implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The internal user id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_GENERATOR")
    @SequenceGenerator(name = "USER_GENERATOR", sequenceName = "USER_SEQUENCE", allocationSize = 1)
    private Long id;

    /**
     * A unique key used to identify the user.
     */
    @Column(nullable = false)
    private String xkey;

    /**
     * The full name of the user
     */
    @Column(nullable = false)
    private String name;

    /**
     * The user's email address.
     */
    @Column(unique = true,nullable = false)
    private String email;

    /**
     * The user's password. Stored as a char array for security.
     */
    @Column(nullable = false)
    private char[] password;

    /**
     * The set of phone numbers associated with the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Phone> phones;

    /**
     * The list of tokens associated with the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Token> tokens;
}
