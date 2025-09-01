package com.mahdy.bookstore.bookservice.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author Mehdi Kamali
 * @since 01/09/2025
 */
@Entity
@Table(name = "USER_ROLE")
@Getter
@Setter
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "ROLE_NAME", columnDefinition = "VARCHAR(50)", unique = true, nullable = false)
    private String roleName;

    @ManyToMany(mappedBy = "userRoles")
    private Set<UserEntity> users;
}
