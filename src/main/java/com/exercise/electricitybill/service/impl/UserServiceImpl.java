package com.exercise.electricitybill.service.impl;

import com.exercise.electricitybill.dto.request.UserRequest;
import com.exercise.electricitybill.dto.response.UserResponse;
import com.exercise.electricitybill.entity.User;
import com.exercise.electricitybill.exception.AppException;
import com.exercise.electricitybill.exception.ErrorCode;
import com.exercise.electricitybill.mapper.UserMapper;
import com.exercise.electricitybill.repository.UserRepository;
import com.exercise.electricitybill.service.UserService;
import com.exercise.electricitybill.utils.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public UserResponse createUser(UserRequest userRequest){
        if(userRepository.existsByUsername(userRequest.getUsername())){
            log.info("username existed:{}",userRequest.getUsername());
            throw  new AppException(ErrorCode.USER_EXITED);
        }
        if(userRepository.existsByEmail(userRequest.getEmail())){
            log.info("email existed:{}",userRequest.getEmail());
            throw  new AppException(ErrorCode.EMAIL_EXITED);
        }
        User user= userMapper.toUser(userRequest);
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(Role.USER.name());
        userRepository.save(user);
        return  userMapper.toUserResponse(user);
    }

    @Override
    @PreAuthorize("hasRole('ELECTRICIAN')")
    public List<UserResponse> getAllUser(){
        var users= userRepository.findAll();
        return users.stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse updateUser(Integer userId, UserRequest userRequest){
        User user= userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXITED));
        log.info("User with id :{} update successful ",userId);
        userMapper.updateUser(user, userRequest);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('ELECTRICIAN')")
    public void deleteUser(Integer userId){
        log.info("delete user with id: {} successful",userId);
        userRepository.deleteById(userId);
    }

    @Override
    public UserResponse getUserById(Integer userId){
        User user=userRepository.findById(userId)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXITED));
        return  userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getMyInfo(){
        var context= SecurityContextHolder.getContext();
        log.info("Authentication: {}", context.getAuthentication());
        log.info("Username: {}", context.getAuthentication().getName());
        String name=context.getAuthentication().getName();
        User user=userRepository.findByUsername(name).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXITED));
        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getALlUsersByPageable(int pageNo, int pageSize){
        Pageable pageable= PageRequest.of(pageNo,pageSize);
        Page<User> users = userRepository.findAll(pageable);
        return users.stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public List<UserResponse> getALlUsersByPageableWithSortBy(int pageNo, int pageSize,String sortBy){
        //pageable với truyền sắp xếp trên thuộc tính vd id:desc
        List<Sort.Order> sorts =new ArrayList<>();
        if(StringUtils.hasLength(sortBy)){
            Pattern pattern=Pattern.compile("(\\w+?)(\\s?:\\s?)(.*)");//moi dau ngoac la 1 group trong day co 3 group bat dau tu group 1
            Matcher matcher=pattern.matcher(sortBy);
            if(matcher.find()){
                if(matcher.group(3).equalsIgnoreCase("asc")){
                    sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else if(matcher.group(3).equalsIgnoreCase("desc")){
                    sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }else{
                    throw new AppException(ErrorCode.ASC_DESC_INVALID);
                }
            }
        }
        //khi truyen 1 asc or desc co dinh ta co the dung :Pageable pageable= PageRequest.of(pageNo,pageSize, Sort.by(Sort.Direction.DESC,sortBy));
        Pageable pageable= PageRequest.of(pageNo,pageSize, Sort.by(sorts));
        Page<User> users = userRepository.findAll(pageable);
        return users.stream().map(userMapper::toUserResponse).toList();
    }
}
