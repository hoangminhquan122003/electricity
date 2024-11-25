package com.exercise.electricitybill.dto.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UsageHistoryRequest {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Timestamp date;
    @Min(value = 1, message = "KWH_USED_INVALID")
    int kwhUsed;
    Integer userId;

}
