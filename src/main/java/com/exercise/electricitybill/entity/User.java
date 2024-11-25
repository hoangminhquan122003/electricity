package com.exercise.electricitybill.entity;

import com.exercise.electricitybill.utils.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer userId;

    @Column(unique = true)
    String username;

    String password;

    String role;

    @OneToMany(mappedBy = "user",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY)
    List<UsageHistory> usageHistories;
}
