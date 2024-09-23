package com.tuandang.student_manager.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "teachers")
public class Teacher implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    @Column(name = "gender")
    boolean gender;
    @NaturalId(mutable = true)
    @Column(name = "email")
    String email;
    @Column(name = "phone_number")
    String phoneNumber;
    @Column(name = "department")
    String department;
    @OneToOne
    @JoinColumn(name = "userId")
    User user;
}
