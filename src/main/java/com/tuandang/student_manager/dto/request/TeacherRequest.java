package com.tuandang.student_manager.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherRequest {
    String firstName;
    String lastName;
    boolean gender;
    String email;
    String phoneNumber;
    String department;
}
