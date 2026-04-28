package com.uptc.uptc;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class UptcUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(
        name       = "user_seq",
        sequenceName = "user_seq",      // nombre real en PostgreSQL
        allocationSize = 50             // debe coincidir con el increment de la secuencia
    )
    @Column(name = "id")
    private Long id;

    private String name;
    @Column(unique = true)
    private String email;
    private String password;
}