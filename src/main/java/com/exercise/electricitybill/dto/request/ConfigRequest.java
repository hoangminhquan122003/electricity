package com.exercise.electricitybill.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ConfigRequest {
    String configName;

    int minKwh;

    Integer maxKwh;

    double pricePerKwh;
}
