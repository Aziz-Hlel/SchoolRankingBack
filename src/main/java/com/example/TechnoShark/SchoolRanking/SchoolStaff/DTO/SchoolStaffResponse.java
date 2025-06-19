package com.example.TechnoShark.SchoolRanking.SchoolStaff.DTO;

import java.time.LocalDate;
import java.util.List;

import com.example.TechnoShark.SchoolRanking.Enums.CountryEnums;
import com.example.TechnoShark.SchoolRanking.Enums.LanguageEnums;

public record SchoolStaffResponse(
        String leadershipTeam,
        String leadershipProfileLink,
        int staffSizeEstimate,
        String teacherQualifications,
        List<CountryEnums> teacherNationalities,
        List<LanguageEnums> teacherLanguages,
        String professionalDevelopment,
        LocalDate lastInspectionDate) {
}
