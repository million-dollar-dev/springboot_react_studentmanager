package com.tuandang.student_manager.dto.request;

import com.tuandang.student_manager.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentRequest {
    String firstName;
    String lastName;
    boolean gender;
    String email;
    String phoneNumber;
    String department;
    String className;
}
