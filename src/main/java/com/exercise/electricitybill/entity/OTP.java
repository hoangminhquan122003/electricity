package com.exercise.electricitybill.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String email;

    String otp;

    LocalDateTime createdAt;

    LocalDateTime expirationAt;

}
