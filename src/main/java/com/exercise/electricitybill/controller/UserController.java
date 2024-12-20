package com.exercise.electricitybill.controller;

import com.exercise.electricitybill.dto.request.UserRequest;
import com.exercise.electricitybill.dto.response.ApiResponse;
import com.exercise.electricitybill.dto.response.UserResponse;
import com.exercise.electricitybill.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/users")
@Slf4j
public class UserController {
    UserService userService;

    @Operation(summary = "create user", description = "send a request to create user ")
    @PostMapping
    public ApiResponse<UserResponse> createUser( @Valid @RequestBody UserRequest userRequest){
        return ApiResponse.<UserResponse>builder()
                .message("user create successful")
                .result(userService.createUser(userRequest))
                .build();
    }

    @Operation(summary = "get all users", description = "send a request to get all users ")
    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<UserResponse>>builder()
                .message("get all users")
                .result(userService.getAllUser())
                .build();
    }

    @Operation(summary = "update user ", description = "send a request to update user by id")
    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Integer userId, @RequestBody UserRequest userRequest){
        return ApiResponse.<UserResponse>builder()
                .message("update user successful")
                .result(userService.updateUser(userId,userRequest))
                .build();
    }

    @Operation(summary = "delete user", description = "send a request to delete user by id ")
    @DeleteMapping("/{userId}")
    public ApiResponse<Void> deleteUser(@PathVariable Integer userId){
        return ApiResponse.<Void>builder()
                .message("delete user successful")
                .build();
    }

    @Operation(summary = "get user by id ", description = "send a request to get user by id ")
    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Integer userId){
        return ApiResponse.<UserResponse>builder()
                .message("get user by id")
                .result(userService.getUserById(userId))
                .build();
    }

    @Operation(summary = "pageable ", description = "send a request to get all user by pageable")
    @GetMapping("/pageable")
    public ApiResponse<List<UserResponse>> getAllUserByPageable( @Min(value = 0  ,message = "PAGE_NO_INVALID") @RequestParam(defaultValue = "0",required = false) int pageNo,
                                                          @Min(value = 10 ,message = "PAGE_SIZE_INVALID") @RequestParam(defaultValue = "10",required = false) int pageSize){
        return ApiResponse.<List<UserResponse>>builder()
                .message("get user by pageable")
                .result(userService.getALlUsersByPageable(pageNo,pageSize))
                .build();
    }

    @Operation(summary = "pageable by property with sort", description = "send a request to pageable by property with sort ")
    @GetMapping("/pageableByProperty")
    public ApiResponse<List<UserResponse>> getAllUserByPageableWithSortBy(@Min(value = 0  ,message = "PAGE_NO_INVALID") @RequestParam(defaultValue = "0",required = false) int pageNo,
                                                                @Min(value = 10,message = "PAGE_SIZE_INVALID") @RequestParam(defaultValue = "10",required = false) int pageSize,
                                                                        @RequestParam(required = false) String sortBy){
        return  ApiResponse.<List<UserResponse>>builder()
                .message("get user by pageable with property ")
                .result(userService.getALlUsersByPageableWithSortBy(pageNo,pageSize,sortBy))
                .build();
    }
}
