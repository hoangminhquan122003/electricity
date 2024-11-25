package com.exercise.electricitybill.mapper;

import com.exercise.electricitybill.dto.request.ConfigRequest;
import com.exercise.electricitybill.dto.response.ConfigResponse;
import com.exercise.electricitybill.entity.Config;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ConfigMapper {
    Config toConfig(ConfigRequest configRequest);
    ConfigResponse toConfigResponse(Config config);
    void updateConfig(@MappingTarget Config config, ConfigRequest configRequest);

}
