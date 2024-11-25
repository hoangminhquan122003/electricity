package com.exercise.electricitybill.controller;

import com.exercise.electricitybill.dto.request.ConfigRequest;
import com.exercise.electricitybill.dto.response.ApiResponse;
import com.exercise.electricitybill.dto.response.ConfigResponse;
import com.exercise.electricitybill.service.ConfigService;
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
    @PostMapping()
    public ApiResponse<ConfigResponse> createConfig(@RequestBody ConfigRequest configRequest){
        return ApiResponse.<ConfigResponse>builder()
                .result(configService.createConfig(configRequest))
                .message("create config successful")
                .build();
    }
    @GetMapping()
    public ApiResponse<List<ConfigResponse>> getAllConfig(){

        return ApiResponse.<List<ConfigResponse>>builder()
                .result(configService.getAllConfig())
                .message("get all config")
                .build();
    }
    @PutMapping("/{configId}")
    public ApiResponse<ConfigResponse> updateConfig(@PathVariable("configId") Integer configId ,@RequestBody ConfigRequest configRequest){
        return ApiResponse.<ConfigResponse>builder()
                .result(configService.updateConfig(configId,configRequest))
                .message("update config successful")
                .build();
    }
    @DeleteMapping("/{configId}")
    public ApiResponse<Void> deleteConfig(@PathVariable Integer configId){
        configService.deleteConfig(configId);
        return  ApiResponse.<Void>builder()
                .message("delete config successful")
                .build();
    }
}
