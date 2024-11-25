package com.exercise.electricitybill.mapper;

import com.exercise.electricitybill.dto.request.UsageHistoryRequest;
import com.exercise.electricitybill.dto.response.UsageHistoryResponse;
import com.exercise.electricitybill.entity.UsageHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsageHistoryMapper {
    //UsageHistory toUsageHistory(UsageHistoryRequest usageHistoryRequest);
    UsageHistoryResponse toUsageHistoryResponse(UsageHistory usageHistory);
    void updateUsageHistory(UsageHistoryRequest usageHistoryRequest,@MappingTarget UsageHistory usageHistory);
}
