package com.mahdy.bookstore.bookservice.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Mehdi Kamali
 * @since 31/08/2025
 */
@Entity
@Table(name = "APPLICATION_USER")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "USERNAME", columnDefinition = "VARCHAR(100)", nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(256)", nullable = false)
    private String password;

    @Column(name = "LAST_LOGIN_TIME", columnDefinition = "TIMESTAMP")
    private LocalDateTime lastLoginTime;

    @Column(name = "REFRESH_TOKEN", columnDefinition = "VARCHAR(128)")
    private String refreshToken;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "APPLICATION_USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<UserRoleEntity> userRoles;
}
