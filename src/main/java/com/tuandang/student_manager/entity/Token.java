package com.tuandang.student_manager.entity;

import ch.qos.logback.core.boolex.EvaluationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "token")
public class Token extends AbstractEntity<Integer> implements Serializable {
    @Column(name = "username", unique = true)
    String username;
    @Column(name = "access_token")
    String accessToken;
    @Column(name = "refresh_token")
    String refreshToken;
    @Column(name = "reset_token")
    private String resetToken;
}
