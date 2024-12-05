package com.exercise.electricitybill.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String username;

    String password;

    @NotBlank(message = "EMAIL_BLANK")
    @Email(message = "EMAIL_INVALID")
    String email;

}
