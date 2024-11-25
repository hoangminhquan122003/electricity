package com.exercise.electricitybill.dto.response;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ConfigResponse {
    Integer configId;

    String configName;

    int minKwh;

    Integer maxKwh;

    double pricePerKwh;
}
