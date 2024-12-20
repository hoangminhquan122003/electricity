package com.exercise.electricitybill.service;

import com.exercise.electricitybill.dto.request.UsageHistoryRequest;
import com.exercise.electricitybill.dto.response.UsageHistoryResponse;
import com.exercise.electricitybill.dto.response.UserResponse;
import com.exercise.electricitybill.entity.Config;
import com.exercise.electricitybill.entity.UsageHistory;
import com.exercise.electricitybill.entity.User;
import com.exercise.electricitybill.exception.AppException;
import com.exercise.electricitybill.exception.ErrorCode;
import com.exercise.electricitybill.mapper.UsageHistoryMapper;
import com.exercise.electricitybill.repository.ConfigRepository;
import com.exercise.electricitybill.repository.UsageHistoryRepository;
import com.exercise.electricitybill.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.List;

public interface UsageHistoryService {

    UsageHistoryResponse createUsageHistory(UsageHistoryRequest request);

    List<UsageHistoryResponse> getAllUsageHistory();

    void deleteUsageHistory(Integer historyId);

    List<UsageHistoryResponse> getUserUsageHistory();
}
