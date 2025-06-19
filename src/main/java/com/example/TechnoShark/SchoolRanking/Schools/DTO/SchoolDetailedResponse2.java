package com.example.TechnoShark.SchoolRanking.Schools.DTO;

import com.example.TechnoShark.SchoolRanking.SchoolAcademics.DTO.SchoolAcademicsResponse;
import com.example.TechnoShark.SchoolRanking.SchoolFacilities.DTO.SchoolFacilitiesResponse;
import com.example.TechnoShark.SchoolRanking.SchoolMedia.DTO.SchoolMediaResponse;
import com.example.TechnoShark.SchoolRanking.SchoolStaff.DTO.SchoolStaffResponse;

public record SchoolDetailedResponse2(
        SchoolResponse schoolGeneral,
        SchoolStaffResponse schoolStaff,
        SchoolFacilitiesResponse schoolFacilities,
        SchoolMediaResponse schoolMedia,
        SchoolAcademicsResponse schoolAcademics) {

}
