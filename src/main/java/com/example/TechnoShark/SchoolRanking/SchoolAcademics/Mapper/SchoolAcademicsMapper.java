package com.example.TechnoShark.SchoolRanking.SchoolAcademics.Mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.TechnoShark.SchoolRanking.SchoolAcademics.DTO.SchoolAcademicsRequest;
import com.example.TechnoShark.SchoolRanking.SchoolAcademics.DTO.SchoolAcademicsResponse;
import com.example.TechnoShark.SchoolRanking.SchoolAcademics.Model.SchoolAcademics;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;

@Mapper(componentModel = "spring")
public interface SchoolAcademicsMapper {

    @Mapping(source = "school", target = "school")
    @Mapping(target = "id", ignore = true) // auto-generated
    SchoolAcademics toEntity(SchoolAcademicsRequest Dto, School school);

    @Mapping(target = "id", source = "schooId")
    @Mapping(target = "school", ignore = true)
    SchoolAcademics updateEntity(SchoolAcademicsRequest Dto, UUID schooId,
            @MappingTarget SchoolAcademics schoolAcademics);


    SchoolAcademicsResponse toDto(SchoolAcademics Entity);
}
