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

import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    List<UserResponse> getAllUser();

    UserResponse updateUser(Integer userId, UserRequest userRequest);

    void deleteUser(Integer userId);

    UserResponse getUserById(Integer userId);

    UserResponse getMyInfo();

    List<UserResponse> getALlUsersByPageable(int pageNo, int pageSize);

    List<UserResponse> getALlUsersByPageableWithSortBy(int pageNo, int pageSize,String sortBy);
}
