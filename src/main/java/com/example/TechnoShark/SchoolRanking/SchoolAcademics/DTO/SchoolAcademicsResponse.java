package com.example.TechnoShark.SchoolRanking.SchoolAcademics.DTO;

import java.util.Set;
import java.util.UUID;

import com.example.TechnoShark.SchoolRanking.Enums.AccreditationEnums;
import com.example.TechnoShark.SchoolRanking.Enums.CurriculumEnums;
import com.example.TechnoShark.SchoolRanking.Enums.LevelsEnums;

public record SchoolAcademicsResponse(
                UUID id,
                int languagesOfInstruction,
                Set<AccreditationEnums> internationalAccreditations,
                String accreditationDocsLinks,
                Set<LevelsEnums> levelsOffered,
                Set<CurriculumEnums> curriculums) {


}