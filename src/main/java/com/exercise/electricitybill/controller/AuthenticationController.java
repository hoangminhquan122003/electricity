package com.exercise.electricitybill.controller;

import com.exercise.electricitybill.dto.request.AuthenticationRequest;
import com.exercise.electricitybill.dto.request.IntrospectRequest;
import com.exercise.electricitybill.dto.response.ApiResponse;
import com.exercise.electricitybill.dto.response.AuthenticationResponse;
import com.exercise.electricitybill.dto.response.IntrospectResponse;
import com.exercise.electricitybill.service.AuthenticationService;
import com.exercise.electricitybill.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    UserService userService;
    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ApiResponse.<AuthenticationResponse>builder()
                .message("login successful")
                .result(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request){
        return  ApiResponse.<IntrospectResponse>builder()
                .result(authenticationService.introspect(request))
                .message("the account is active")
                .build();
    }

}
