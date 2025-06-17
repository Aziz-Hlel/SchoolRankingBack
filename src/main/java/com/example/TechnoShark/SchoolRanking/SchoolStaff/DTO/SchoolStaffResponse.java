package com.example.TechnoShark.SchoolRanking.SchoolStaff.DTO;

import java.time.LocalDate;
import java.util.List;

import com.example.TechnoShark.SchoolRanking.Enums.Country;
import com.example.TechnoShark.SchoolRanking.Enums.Languages;

public record SchoolStaffResponse(
        String leadershipTeam,
        String leadershipProfileLink,
        int staffSizeEstimate,
        String teacherQualifications,
        List<Country> teacherNationalities,
        List<Languages> teacherLanguages,
        String professionalDevelopment,
        LocalDate lastInspectionDate) {
}
