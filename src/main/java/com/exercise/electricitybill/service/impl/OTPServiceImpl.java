package com.exercise.electricitybill.service.impl;

import com.exercise.electricitybill.entity.OTP;
import com.exercise.electricitybill.exception.AppException;
import com.exercise.electricitybill.exception.ErrorCode;
import com.exercise.electricitybill.repository.OTPRepository;
import com.exercise.electricitybill.service.OTPService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Slf4j
public class OTPServiceImpl implements OTPService {
    OTPRepository otpRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public String generateOTP(String email){
        otpRepository.deleteByEmail(email);
        SecureRandom random = new SecureRandom();
        String otp=String.format("%06d",random.nextInt(1000000));
        String hashOTP=passwordEncoder.encode(otp);
        OTP otpEntity=OTP.builder()
                .email(email)
                .otp(hashOTP)
                .createdAt(LocalDateTime.now())
                .expirationAt(LocalDateTime.now().plusMinutes(1))
                .build();
        otpRepository.save(otpEntity);
        return otp;
    }

    @Override
    public boolean validateOTP(String email, String otp){
        var otpEntity=otpRepository.findByEmail(email)
                .orElseThrow(()->new AppException(ErrorCode.EMAIL_NOT_EXITED));

        boolean isValid= passwordEncoder.matches(otp,otpEntity.getOtp());
        if (otpEntity.getExpirationAt().isBefore(LocalDateTime.now()) || !isValid) {
            otpRepository.deleteByEmail(email);
            return false;
        }
        return  true;
    }

    @Override
    @Scheduled(cron="0 */5 * * * *")
    public void cleanOTP(){
        LocalDateTime date=LocalDateTime.now();
        otpRepository.deleteByExpirationAtBefore(date);
        log.info("Expired OTPs have been deleted at {} ",date);
    }
}
