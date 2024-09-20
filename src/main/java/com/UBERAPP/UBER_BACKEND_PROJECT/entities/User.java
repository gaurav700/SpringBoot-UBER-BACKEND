package com.UBERAPP.UBER_BACKEND_PROJECT.entities;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "uber_user",
indexes = {
        @Index(name = "index_email", columnList = "email")
})
@Getter
@Setter

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
