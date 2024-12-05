package com.exercise.electricitybill.controller;

import com.exercise.electricitybill.dto.request.UserRequest;
import com.exercise.electricitybill.dto.response.ApiResponse;
import com.exercise.electricitybill.dto.response.UserResponse;
import com.exercise.electricitybill.service.UserService;
import jakarta.validation.Valid;
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
    @PostMapping
    public ApiResponse<UserResponse> createUser( @Valid @RequestBody UserRequest userRequest){
        return ApiResponse.<UserResponse>builder()
                .message("user create successful")
                .result(userService.createUser(userRequest))
                .build();
    }
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
    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Integer userId, @RequestBody UserRequest userRequest){
        return ApiResponse.<UserResponse>builder()
                .message("update user successful")
                .result(userService.updateUser(userId,userRequest))
                .build();
    }
    @DeleteMapping("/{userId}")
    public ApiResponse<Void> deleteUser(@PathVariable Integer userId){
        return ApiResponse.<Void>builder()
                .message("delete user successful")
                .build();
    }
    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Integer userId){
        return ApiResponse.<UserResponse>builder()
                .message("get user by id")
                .result(userService.getUserById(userId))
                .build();
    }
}
