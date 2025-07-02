package com.example.TechnoShark.SchoolRanking.Users.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.TechnoShark.SchoolRanking.Users.DTO.UpdateUserRequest;
import com.example.TechnoShark.SchoolRanking.Users.DTO.UserPageResponse;
import com.example.TechnoShark.SchoolRanking.Users.DTO.UserResponse;
import com.example.TechnoShark.SchoolRanking.Users.Model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toDto(User Entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "userSchools", ignore = true)
    User toUpdatedEntity(UpdateUserRequest userRequest, @MappingTarget User Entity);

    @Mapping(target = "username", expression = "java(firstName + \" \" + lastName)")
    UserPageResponse toPageDto(User Entity);

}
