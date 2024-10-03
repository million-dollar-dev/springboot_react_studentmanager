package com.tuandang.student_manager.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenResponse implements Serializable {
    String accessToken;
    String refreshToken;

    //more Over
}
