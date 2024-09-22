package com.nisum.oppenheimer.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a Token entity in the application.
 *
 * This class is mapped to the "TOKENS" table in the database and extends the
 * {@link Auditable} class to include common auditing fields. It utilizes Lombok
 * annotations to reduce boilerplate code for getters, setters, and builders.
 *
 * <p>Key features include:</p>
 * <ul>
 *     <li>Support for token status management through an enumerated type.</li>
 *     <li>Many-to-one relationship with the User entity.</li>
 *     <li>Serialization support with a defined {@code serialVersionUID}.</li>
 * </ul>
 *
 * @see Auditable
 * @see User
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Table(name="TOKENS")
@Access(AccessType.FIELD)
@ToString(exclude = "user")
public class Token extends Auditable implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    /**
     * Enum representing the status of the token.
     *
     * Possible values are:
     * <ul>
     *     <li>ENABLE - Indicates the token is active.</li>
     *     <li>DISABLE - Indicates the token is inactive.</li>
     * </ul>
     */
    enum TokenStatus {ENABLE, DISABLE}

    /**
     * The internal token id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOKEN_GENERATOR")
    @SequenceGenerator(name = "TOKEN_GENERATOR", sequenceName = "TOKEN_SEQUENCE", allocationSize = 1)
    private Long id;

    /**
     * A unique key used to identify the token.
     */
    @Column(nullable = false)
    private String xkey;

    /**
     * The JWT (JSON Web Token) associated with this token.
     */
    @Column(nullable = false)
    private String jwt;

    /**
     * The status of the token.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenStatus status;

    /**
     * The user associated with this token.
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
