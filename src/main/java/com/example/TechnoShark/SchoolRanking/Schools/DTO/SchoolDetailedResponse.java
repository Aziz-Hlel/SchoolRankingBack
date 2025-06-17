package com.example.TechnoShark.SchoolRanking.Schools.DTO;

import java.util.UUID;

import com.example.TechnoShark.SchoolRanking.Enums.Country;
import com.example.TechnoShark.SchoolRanking.Enums.SchoolType;
import com.example.TechnoShark.SchoolRanking.SchoolAcademics.DTO.SchoolAcademicsResponse;
import com.example.TechnoShark.SchoolRanking.SchoolFacilities.DTO.SchoolFacilitiesResponse;
import com.example.TechnoShark.SchoolRanking.SchoolMedia.DTO.SchoolMediaResponse;
import com.example.TechnoShark.SchoolRanking.SchoolStaff.DTO.SchoolStaffResponse;

public record SchoolDetailedResponse(
        UUID id,
        String name,
        String city,
        String address,
        String phoneNumber,
        String email,
        Integer yearEstablished,
        SchoolType type,
        String website,
        Country country,
        SchoolStaffResponse schoolStaff,
        SchoolFacilitiesResponse schoolFacilities,
        SchoolMediaResponse schoolMedia,
        SchoolAcademicsResponse schoolAcademics) {

}
