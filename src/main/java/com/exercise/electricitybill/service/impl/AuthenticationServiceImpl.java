package com.exercise.electricitybill.service.impl;

import com.exercise.electricitybill.dto.request.AuthenticationRequest;
import com.exercise.electricitybill.dto.request.IntrospectRequest;
import com.exercise.electricitybill.dto.request.LogoutRequest;
import com.exercise.electricitybill.dto.request.RefreshRequest;
import com.exercise.electricitybill.dto.response.AuthenticationResponse;
import com.exercise.electricitybill.dto.response.IntrospectResponse;
import com.exercise.electricitybill.entity.InvalidedToken;
import com.exercise.electricitybill.entity.User;
import com.exercise.electricitybill.exception.AppException;
import com.exercise.electricitybill.exception.ErrorCode;
import com.exercise.electricitybill.repository.InvalidatedTokenRepository;
import com.exercise.electricitybill.repository.UserRepository;
import com.exercise.electricitybill.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signer-key}")
    String SIGNER_KEY;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        User user=userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXITED));
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder(10);
        boolean isAuthenticated =passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if(!isAuthenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token=generateToken(user);
        return  AuthenticationResponse.builder()
                .token(token)
                .build();

    }

    private String generateToken(User user) {
        JWSHeader jwsHeader=new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet=new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("userId",user.getUserId())
                .claim("scope","ROLE_"+user.getRole())
                .build();
        Payload payload=new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject=new JWSObject(jwsHeader,payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        }catch (JOSEException e){
            log.error("token not create");
            throw new RuntimeException(e);
        }
    }
    @Override
    public IntrospectResponse introspect(IntrospectRequest introspectRequest){
        String token=introspectRequest.getToken();
        boolean isValid=true;
        try{
            verifyToken(token);
        }catch (AppException | JOSEException | ParseException e){
            isValid=false;
        }
        return IntrospectResponse.builder()
                .isAuthenticated(isValid)
                .build();
    }


    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier jwsVerifier=new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT=SignedJWT.parse(token);
        Date expirationTime =signedJWT.getJWTClaimsSet().getExpirationTime();
        //xac minh chu ky cua token
        boolean isVerified = signedJWT.verify(jwsVerifier);
        if(!(isVerified &&expirationTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        if (invalidatedTokenRepository
                .existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        return signedJWT;
    }
    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        var signToken=verifyToken(request.getToken());
        String jit=signToken.getJWTClaimsSet().getJWTID();
        Date expirationTime= signToken.getJWTClaimsSet().getExpirationTime();
        InvalidedToken invalidedToken=InvalidedToken.builder()
                .id(jit)
                .expirationTime(expirationTime)
                .build();
        log.info("logout with id:{}",jit);
        invalidatedTokenRepository.save(invalidedToken);
    }
    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken());
        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        InvalidedToken invalidatedToken = InvalidedToken.builder()
                .id(jit)
                .expirationTime(expiryTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
        var username = signedJWT.getJWTClaimsSet().getSubject();
        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.UNAUTHENTICATED)
        );
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    //xoa vao moi toi
    @Async
    @Override
    public void deleteAllInvalidToken(){
        log.info("delete {} invalid token in db",invalidatedTokenRepository.count());
        invalidatedTokenRepository.deleteAll();
    }
    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void scheduledTokenCleanUp(){
        log.info("Starting scheduled token cleanup...");
        deleteAllInvalidToken();
    }
}
