package com.nisum.oppenheimer.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Builder
@Table(name="PHONES")
@Access(AccessType.FIELD)
@ToString(exclude = "user")
public class Phone extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PHONE_GENERATOR")
    @SequenceGenerator(name = "PHONE_GENERATOR", sequenceName = "PHONE_SEQUENCE", allocationSize = 1)
    private Long id;
    private String xkey;
    private Long number;
    private Short cityCode;
    private Short countryCode;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
