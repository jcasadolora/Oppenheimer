package com.nisum.oppenheimer.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Builder
@Table(name="TOKENS")
@Access(AccessType.FIELD)
@ToString(exclude = "user")
public class Token extends Auditable {

    enum TokenStatus {ENABLE, DISABLE}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOKEN_GENERATOR")
    @SequenceGenerator(name = "TOKEN_GENERATOR", sequenceName = "TOKEN_SEQUENCE", allocationSize = 1)
    private Long id;
    private String xkey;
    private String jwt;

    @Enumerated(EnumType.STRING)
    private TokenStatus status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
