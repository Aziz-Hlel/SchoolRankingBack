package com.example.TechnoShark.SchoolRanking.Schools.Mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolDetailedResponse;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolRequest;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolResponse;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;
import com.example.TechnoShark.SchoolRanking.Users.Model.User;

@Mapper(componentModel = "spring")
public interface SchoolMapper {

    default User map(String userId) {
        if (userId == null)
            return null;
        User user = new User();
        user.setId(UUID.fromString(userId));
        return user;
    }

    @Mapping(target = "id", ignore = true) // You let DB handle this
    @Mapping(target = "email", source = "dto.email")
    @Mapping(target = "schoolAcademics", ignore = true)
    @Mapping(target = "schoolFacilities", ignore = true)
    @Mapping(target = "schoolMedia", ignore = true)
    @Mapping(target = "schoolStaff", ignore = true)
    School toEntity(SchoolRequest dto, User user);

    @Mapping(target = "id", source = "schoolId")
    @Mapping(target = "user", source = "userId")
    @Mapping(target = "schoolAcademics", ignore = true)
    @Mapping(target = "schoolFacilities", ignore = true)
    @Mapping(target = "schoolMedia", ignore = true)
    @Mapping(target = "schoolStaff", ignore = true)
    void updateSchoolFromDto(SchoolRequest dto, UUID schoolId, UUID userId, @MappingTarget School entity);

    SchoolResponse toDto(School school);

    SchoolDetailedResponse toDetailedDto(School school);


}
