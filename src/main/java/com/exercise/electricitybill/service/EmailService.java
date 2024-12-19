package com.exercise.electricitybill.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class EmailService {
    JavaMailSender javaMailSender;
    OTPService otpService;
    @NonFinal
    @Value("${spring.mail.username}")
    private String emailFrom;
    @Async
    public String sendMail(String to, String subject, String body , MultipartFile[] files) throws MessagingException {
        String otp=otpService.generateOTP(to);
        String formatBody=String.format(body+" \nOTP của bạn là :%s", otp);

        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true,"UTF-8");
        mimeMessageHelper.setFrom(emailFrom);
        if(to.contains(",")){
            mimeMessageHelper.setTo(InternetAddress.parse(to));
        }else{
            mimeMessageHelper.setTo(to);
        }
        if(files!=null){
            for(MultipartFile file: files){
                mimeMessageHelper.addAttachment(file.getOriginalFilename(),file);
            }
        }
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(formatBody,true);
        javaMailSender.send(mimeMessage);
        return otp;
    }
}
