package com.exercise.electricitybill.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Integer configId;

    String configName;

    int minKwh;

    Integer maxKwh;

    double pricePerKwh;
}
