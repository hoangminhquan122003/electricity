package com.exercise.electricitybill.service;

import com.exercise.electricitybill.dto.request.UserRequest;
import com.exercise.electricitybill.dto.response.UserResponse;
import com.exercise.electricitybill.entity.Config;
import com.exercise.electricitybill.entity.User;
import com.exercise.electricitybill.exception.AppException;
import com.exercise.electricitybill.exception.ErrorCode;
import com.exercise.electricitybill.mapper.UserMapper;
import com.exercise.electricitybill.repository.UserRepository;
import com.exercise.electricitybill.utils.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public UserResponse createUser(UserRequest userRequest){
        if(userRepository.existsByUsername(userRequest.getUsername())){
            log.info("username existed:{}",userRequest.getUsername());
            throw  new AppException(ErrorCode.USER_EXITED);
        }
        User user= userMapper.toUser(userRequest);
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(Role.USER.name());
        userRepository.save(user);
        return  userMapper.toUserResponse(user);
    }
    @PreAuthorize("hasRole('ELECTRICIAN')")
    public List<UserResponse> getAllUser(){
        var users= userRepository.findAll();
        return users.stream().map(userMapper::toUserResponse).toList();
    }
    public UserResponse updateUser(Integer userId, UserRequest userRequest){
        User user= userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXITED));
        log.info("User with id :{} update successful ",userId);
        userMapper.updateUser(user, userRequest);
        return userMapper.toUserResponse(userRepository.save(user));
    }
    @PreAuthorize("hasRole('ELECTRICIAN')")
    public void deleteUser(Integer userId){
        log.info("delete user with id: {} successful",userId);
        userRepository.deleteById(userId);
    }
    public UserResponse getUserById(Integer userId){
        User user=userRepository.findById(userId)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXITED));
        return  userMapper.toUserResponse(user);
    }
    public UserResponse getMyInfo(){
        var context= SecurityContextHolder.getContext();
        log.info("Authentication: {}", context.getAuthentication());
        log.info("Username: {}", context.getAuthentication().getName());
        String name=context.getAuthentication().getName();
        User user=userRepository.findByUsername(name).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXITED));
        return userMapper.toUserResponse(user);
    }
}
