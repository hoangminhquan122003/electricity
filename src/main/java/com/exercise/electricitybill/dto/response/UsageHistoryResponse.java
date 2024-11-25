package com.exercise.electricitybill.dto.response;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsageHistoryResponse {
    Integer historyId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Timestamp date;

    int kwhUsed;

    long totalCost;
}
