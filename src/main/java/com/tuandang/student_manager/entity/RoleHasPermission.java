package com.tuandang.student_manager.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "role_has_permission")
public class RoleHasPermission extends AbstractEntity<Integer> implements Serializable {
    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;
    @ManyToOne
    @JoinColumn(name = "permission_id")
    Permission permission;
}
