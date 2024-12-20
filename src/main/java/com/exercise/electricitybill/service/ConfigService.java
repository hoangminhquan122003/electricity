package com.exercise.electricitybill.service;

import com.exercise.electricitybill.dto.request.ConfigRequest;
import com.exercise.electricitybill.dto.response.ConfigResponse;
import com.exercise.electricitybill.entity.Config;
import com.exercise.electricitybill.exception.AppException;
import com.exercise.electricitybill.exception.ErrorCode;
import com.exercise.electricitybill.mapper.ConfigMapper;
import com.exercise.electricitybill.repository.ConfigRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ConfigService {

    ConfigResponse createConfig(ConfigRequest configRequest);

    List<ConfigResponse> getAllConfig();

    void deleteConfig(Integer configId);

    ConfigResponse updateConfig(Integer configId,ConfigRequest configRequest);
}
