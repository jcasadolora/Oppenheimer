package com.nisum.oppenheimer.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@Table(name="USERS")
@Access(AccessType.FIELD)
@ToString(exclude = "phones,tokens")
public class User extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_GENERATOR")
    @SequenceGenerator(name = "USER_GENERATOR", sequenceName = "USER_SEQUENCE", allocationSize = 1)
    private Long id;
    private String xkey;
    private String name;
    private String email;
    private char[] password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Phone> phones;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Token> tokens;
}
