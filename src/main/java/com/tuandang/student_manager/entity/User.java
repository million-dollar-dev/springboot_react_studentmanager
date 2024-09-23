package com.tuandang.student_manager.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Column(columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci", name = "id")
    String username;
    @Column(name = "password")
    String password;
    @OneToMany(mappedBy = "user")
    @Column(name = "roles")
    Set<UserHasRole> userHasRoles = new HashSet<>();

}
