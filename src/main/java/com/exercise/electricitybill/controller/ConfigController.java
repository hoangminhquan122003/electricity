package com.exercise.electricitybill.controller;

import com.exercise.electricitybill.dto.request.ConfigRequest;
import com.exercise.electricitybill.dto.response.ApiResponse;
import com.exercise.electricitybill.dto.response.ConfigResponse;
import com.exercise.electricitybill.service.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/configs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ConfigController {
    private static final Logger log = LoggerFactory.getLogger(ConfigController.class);
    ConfigService configService;

    @Operation(summary = "create config", description = "send a request to create config ")
    @PostMapping()
    public ApiResponse<ConfigResponse> createConfig(@RequestBody ConfigRequest configRequest){
        return ApiResponse.<ConfigResponse>builder()
                .result(configService.createConfig(configRequest))
                .message("create config successful")
                .build();
    }

    @Operation(summary = "get all config", description = "send a request to get all config ")
    @GetMapping()
    public ApiResponse<List<ConfigResponse>> getAllConfig(){

        return ApiResponse.<List<ConfigResponse>>builder()
                .result(configService.getAllConfig())
                .message("get all config")
                .build();
    }

    @Operation(summary = "update config", description = "send a request to update config ")
    @PutMapping("/{configId}")
    public ApiResponse<ConfigResponse> updateConfig(@PathVariable("configId") Integer configId ,@RequestBody ConfigRequest configRequest){
        return ApiResponse.<ConfigResponse>builder()
                .result(configService.updateConfig(configId,configRequest))
                .message("update config successful")
                .build();
    }

    @Operation(summary = "delete", description = "send a request to delete config ")
    @DeleteMapping("/{configId}")
    public ApiResponse<Void> deleteConfig(@PathVariable Integer configId){
        configService.deleteConfig(configId);
        return  ApiResponse.<Void>builder()
                .message("delete config successful")
                .build();
    }
}
