package com.exercise.electricitybill.controller;

import com.exercise.electricitybill.dto.request.UsageHistoryRequest;
import com.exercise.electricitybill.dto.response.ApiResponse;
import com.exercise.electricitybill.dto.response.UsageHistoryResponse;
import com.exercise.electricitybill.service.UsageHistoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/usage-history")
@Slf4j
public class UsageHistoryController {
    UsageHistoryService usageHistoryService;
    @PostMapping
    public ApiResponse<UsageHistoryResponse> createUsageHistory(@RequestBody @Valid UsageHistoryRequest request){
        return ApiResponse.<UsageHistoryResponse>builder()
                .result(usageHistoryService.createUsageHistory(request))
                .message("create usage history successful")
                .build();
    }

    @GetMapping
    public ApiResponse<List<UsageHistoryResponse>> getAllUsageHistory(){
        return  ApiResponse.<List<UsageHistoryResponse>>builder()
                .result(usageHistoryService.getAllUsageHistory())
                .message("get all usage history")
                .build();
    }
    @DeleteMapping("/{historyId}")
    public ApiResponse<Void> deleteUsageHistory(@PathVariable Integer historyId){
        usageHistoryService.deleteUsageHistory(historyId);
        return  ApiResponse.<Void>builder()
                .message("delete successful")
                .build();
    }
    @GetMapping("/user")
    public ApiResponse<List<UsageHistoryResponse>> getUserUsageHistory(){
        return  ApiResponse.<List<UsageHistoryResponse>>builder()
                .message("get user with usage history successful")
                .result(usageHistoryService.getUserUsageHistory())
                .build();
    }
}
