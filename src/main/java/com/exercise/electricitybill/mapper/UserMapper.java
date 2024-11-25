package com.exercise.electricitybill.mapper;

import com.exercise.electricitybill.dto.request.ConfigRequest;
import com.exercise.electricitybill.dto.request.UserRequest;
import com.exercise.electricitybill.dto.response.ConfigResponse;
import com.exercise.electricitybill.dto.response.UserResponse;
import com.exercise.electricitybill.entity.Config;
import com.exercise.electricitybill.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequest userRequest);
    @Mapping(source = "userId",target = "userId")
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserRequest userRequest);
}
