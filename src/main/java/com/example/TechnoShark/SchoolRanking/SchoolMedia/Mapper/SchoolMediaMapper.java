package com.example.TechnoShark.SchoolRanking.SchoolMedia.Mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.TechnoShark.SchoolRanking.SchoolMedia.DTO.SchoolMediaResponse;
import com.example.TechnoShark.SchoolRanking.SchoolMedia.DTO.SchoolMediaRequest;
import com.example.TechnoShark.SchoolRanking.SchoolMedia.Model.SchoolMedia;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;

@Mapper(componentModel = "spring")
public interface SchoolMediaMapper {


    @Mapping(target = "school", source = "school")
    @Mapping(target = "id", ignore = true) // auto-generated
    SchoolMedia toEntity(SchoolMediaRequest school_MediaRequest, School school);

    @Mapping(target = "id", source = "schoolMediaId")
    @Mapping(target = "school", ignore = true)
    SchoolMedia updateEntity(SchoolMediaRequest school_MediaRequest, UUID schoolMediaId,
            @MappingTarget SchoolMedia schoolMedia);

    SchoolMediaResponse toDto(SchoolMedia schoolMedia);
}
