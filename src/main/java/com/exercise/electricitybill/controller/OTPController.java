package com.exercise.electricitybill.controller;

import com.exercise.electricitybill.dto.response.ApiResponse;
import com.exercise.electricitybill.exception.AppException;
import com.exercise.electricitybill.exception.ErrorCode;
import com.exercise.electricitybill.repository.OTPRepository;
import com.exercise.electricitybill.service.EmailService;
import com.exercise.electricitybill.service.OTPService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/otp")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OTPController {
    private static final Logger log = LoggerFactory.getLogger(OTPController.class);
    OTPService otpService;
    EmailService emailService;
    @PostMapping("/send")
    public ApiResponse<String> sendOTP(@RequestParam String to,
                                       @RequestParam String subject,
                                       @RequestParam String body,
                                       @RequestParam(required = false) MultipartFile[] files){
        try {
            return ApiResponse.<String>builder()
                    .message("send email successful")
                    .result(emailService.sendMail(to, subject, body, files))
                    .build();

        }catch (Exception e){
            log.error("Send email are fail, errorMessage: {}",e.getMessage());
            return ApiResponse.<String>builder()
                    .message("send email fail")
                    .result(null)
                    .build();
        }
    }
    @PostMapping("/validate")
    public ApiResponse<Boolean> validateOTP(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = otpService.validateOTP(email, otp);
        return ApiResponse.<Boolean>builder()
                .result(isValid)
                .message("validate otp")
                .build();

    }
}
