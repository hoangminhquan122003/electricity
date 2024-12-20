package com.exercise.electricitybill.controller;

import com.exercise.electricitybill.dto.request.AuthenticationRequest;
import com.exercise.electricitybill.dto.request.IntrospectRequest;
import com.exercise.electricitybill.dto.request.LogoutRequest;
import com.exercise.electricitybill.dto.request.RefreshRequest;
import com.exercise.electricitybill.dto.response.ApiResponse;
import com.exercise.electricitybill.dto.response.AuthenticationResponse;
import com.exercise.electricitybill.dto.response.IntrospectResponse;
import com.exercise.electricitybill.service.AuthenticationService;
import com.exercise.electricitybill.service.UserService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    UserService userService;

    @Operation(summary = "login", description = "send a request to login account ")
    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ApiResponse.<AuthenticationResponse>builder()
                .message("login successful")
                .result(authenticationService.authenticate(request))
                .build();
    }

    @Operation(summary = "logout", description = "send a request to logout account ")
    @PostMapping("/logout")
    public  ApiResponse<Void> logout(@RequestBody LogoutRequest logoutRequest) throws ParseException, JOSEException {
        authenticationService.logout(logoutRequest);
        return ApiResponse.<Void>builder()
                .message("logout successful")
                .build();
    }

    @Operation(summary = "refresh", description = "send a request to refresh token ")
    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @Operation(summary = "introspect", description = "send a request to introspect token ")
    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request){
        return  ApiResponse.<IntrospectResponse>builder()
                .result(authenticationService.introspect(request))
                .message("the account is active")
                .build();
    }

}
