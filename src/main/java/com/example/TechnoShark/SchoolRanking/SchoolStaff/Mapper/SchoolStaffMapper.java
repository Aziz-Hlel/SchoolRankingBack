package com.example.TechnoShark.SchoolRanking.SchoolStaff.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.TechnoShark.SchoolRanking.SchoolStaff.DTO.SchoolStaffRequestDTO;
import com.example.TechnoShark.SchoolRanking.SchoolStaff.DTO.SchoolStaffResponse;
import com.example.TechnoShark.SchoolRanking.SchoolStaff.Model.SchoolStaff;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;

// Mapper is a class that converts from one object to another : Entity to Object and vise versa
@Mapper(componentModel = "spring")
public interface SchoolStaffMapper {

    @Mapping(target = "school", source = "school")
    @Mapping(target = "id", ignore = true)
    SchoolStaff toEntity(SchoolStaffRequestDTO dto, School school);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "school", ignore = true)
    SchoolStaff UpdateEntity(SchoolStaffRequestDTO dto,@MappingTarget SchoolStaff entity);

    SchoolStaffResponse toDTO(SchoolStaff school_Staff);
}
