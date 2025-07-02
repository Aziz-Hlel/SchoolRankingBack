package com.example.TechnoShark.SchoolRanking.Schools.Mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolDetailedResponse;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolDetailedResponse2;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolPageResponse;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolProgressResponse;
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
    @Mapping(target = "formsCompleted", ignore = true)
    @Mapping(target = "lastFormStep", constant = "1")
    public abstract School toEntity(SchoolRequest dto, User user);

    // @AfterMapping
    // protected void addSchoolToUser(@MappingTarget School school, User user) {
    // if (user != null && school != null) {
    // user.addSchool(school);
    // }
    // }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "schoolAcademics", ignore = true)
    @Mapping(target = "schoolFacilities", ignore = true)
    @Mapping(target = "schoolMedia", ignore = true)
    @Mapping(target = "schoolStaff", ignore = true)
    @Mapping(target = "formsCompleted", ignore = true)
    @Mapping(target = "lastFormStep", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    School updateSchoolFromDto(SchoolRequest dto, @MappingTarget School entity);

    public abstract SchoolResponse toDto(School school);

    // default String mapAdminUsername(School school) {
    // if (school.getUser() == null)
    // return null;
    // return school.getUser().getFirstName() + " " +
    // school.getUser().getLastName();
    // }

    @Mapping(target = "adminUsername", constant = "admin") // expression = "java(mapAdminUsername(school))")
    @Mapping(target = "isComplete", source = "formsCompleted")
    SchoolPageResponse toPageDto(School school);

    SchoolDetailedResponse toDetailedDto(School school);

    @Mapping(target = "schoolGeneral", source = "school")
    SchoolDetailedResponse2 toDetailedDto2(School school);

    SchoolProgressResponse toProgressDto(School school);

}
