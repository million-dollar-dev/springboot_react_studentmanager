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
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "permissions")
public class Permission extends AbstractEntity<Integer> implements Serializable {
    String name;
    String description;
    @OneToMany(mappedBy = "permission")
    Set<RoleHasPermission> roleHasPermissions = new HashSet<>();
}
