package com.exercise.electricitybill.dto.response;

import com.exercise.electricitybill.utils.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Integer userId;

    String username;

    String password;

    String email;

    String role;
}
