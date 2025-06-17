package com.example.TechnoShark.SchoolRanking.SchoolFacilities.Mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.TechnoShark.SchoolRanking.SchoolFacilities.DTO.SchoolFacilitiesRequest;
import com.example.TechnoShark.SchoolRanking.SchoolFacilities.DTO.SchoolFacilitiesResponse;
import com.example.TechnoShark.SchoolRanking.SchoolFacilities.Model.SchoolFacilities;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;

@Mapper(componentModel = "spring")
public interface SchoolFacilitiesMapper {

    @Mapping(target = "school", source = "school")
    @Mapping(target = "id", ignore = true)
    SchoolFacilities toEntity(SchoolFacilitiesRequest request, School school);

    @Mapping(target = "id", source = "schoolFacilitiesId")
    @Mapping(target = "school", ignore = true)
    SchoolFacilities updateEntity(SchoolFacilitiesRequest request, UUID schoolFacilitiesId,
            @MappingTarget SchoolFacilities schoolFacilities);

    SchoolFacilitiesResponse toDTO(SchoolFacilities schoolFacilities);
}
