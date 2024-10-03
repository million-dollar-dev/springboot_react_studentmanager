package com.tuandang.student_manager.dto.request;

import com.tuandang.student_manager.util.Platform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignInRequest {
    @NotBlank(message = "username must be not null")
    String username;
    @NotBlank(message = "username must be not null")
    String password;
    // yêu cầu về platform
    @NotNull()
    Platform platform;
    // Tên thị bị để gửi thông báo, ví dụ 1 tài khoản mà nhiều thiết bị di động thì gửi thông báo hết
    String deviceToken;
    // Version trong thiết bị
    String version;
}
