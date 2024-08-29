package com.tuandang.student_manager.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherResponse {
    Long id;
    String firstName;
    String lastName;
    boolean gender;
    String email;
    String phoneNumber;
    String department;
    String className;
}
