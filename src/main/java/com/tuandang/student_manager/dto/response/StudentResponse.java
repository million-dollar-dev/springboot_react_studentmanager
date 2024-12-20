package com.tuandang.student_manager.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentResponse {
    Long id;
    String firstName;
    String lastName;
    boolean gender;
    String email;
    String phoneNumber;
    String department;
    String className;
}
