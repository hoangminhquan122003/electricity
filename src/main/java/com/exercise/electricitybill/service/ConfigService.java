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

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class ConfigService {
    private static final Logger log = LoggerFactory.getLogger(ConfigService.class);
    ConfigRepository configRepository;
    ConfigMapper configMapper;

    @PreAuthorize("hasRole('ELECTRICIAN')")
    public ConfigResponse createConfig(ConfigRequest configRequest){
        if(configRepository.existsByConfigName(configRequest.getConfigName())){
            log.error("config name existed {}",configRequest.getConfigName());
            throw new AppException(ErrorCode.CONFIG_NAME_EXITED);
        }
        Config config= configMapper.toConfig(configRequest);
        configRepository.save(config);
        log.info("Config create successful with id: {}",config.getConfigId());
        return configMapper.toConfigResponse(config);
    }
    @PreAuthorize("hasRole('ELECTRICIAN')")
    public List<ConfigResponse> getAllConfig(){
        var role=configRepository.findAll();
        //map((config)->configMapper.toConfigResponse(config))
        return role.stream().map(configMapper::toConfigResponse).toList();
    }
    @PreAuthorize("hasRole('ELECTRICIAN')")
    public void deleteConfig(Integer configId){
        log.info("Config with id :{} delete successful ",configId);
        configRepository.deleteById(configId);
    }
    @PreAuthorize("hasRole('ELECTRICIAN')")
    public ConfigResponse updateConfig(Integer configId,ConfigRequest configRequest){
        Config config= configRepository.findById(configId)
                .orElseThrow(()-> new AppException(ErrorCode.CONFIG_NAME_NOT_EXITED));
        log.info("Config with id :{} update successful ",configId);
        configMapper.updateConfig(config, configRequest);
        return configMapper.toConfigResponse(configRepository.save(config));
    }
}
