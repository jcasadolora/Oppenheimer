package com.nisum.oppenheimer.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a Phone entity in the application.
 *
 * This class is mapped to the "PHONES" table in the database and extends the
 * {@link Auditable} class to include common auditing fields. It utilizes Lombok
 * annotations to reduce boilerplate code for getters, setters, and builders.
 *
 * <p>Key features include:</p>
 * <ul>
 *     <li>Support for associating a phone number with a user.</li>
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
@Table(name="PHONES")
@Access(AccessType.FIELD)
@ToString(exclude = "user")
public class Phone extends Auditable implements Serializable {

    @Serial
    private static final long serialVersionUID = 3L;

    /**
     * The internal phone id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PHONE_GENERATOR")
    @SequenceGenerator(name = "PHONE_GENERATOR", sequenceName = "PHONE_SEQUENCE", allocationSize = 1)
    private Long id;

    /**
     * Unique identifier for the phone entry.
     */
    @Column(nullable = false)
    private String xkey;

    /**
     * The phone number associated with this entry.
     */
    @Column(nullable = false)
    private Long number;

    /**
     * The city code associated with the phone number.
     */
    @Column(nullable = false)
    private Short cityCode;

    /**
     * The country code associated with the phone number.
     */
    @Column(nullable = false)
    private Short countryCode;

    /**
     * The user associated with this phone entry.
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
