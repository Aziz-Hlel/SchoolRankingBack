package com.example.TechnoShark.SchoolRanking.SchoolAcademics.DTO;

import java.util.Set;
import java.util.UUID;

import com.example.TechnoShark.SchoolRanking.Enums.AccreditationEnums;
import com.example.TechnoShark.SchoolRanking.Enums.CurriculumEnums;
import com.example.TechnoShark.SchoolRanking.Enums.LevelEnums;

public record SchoolAcademicsResponse(
                UUID id,
                int languagesOfInstruction,
                Set<AccreditationEnums> internationalAccreditations,
                String accreditationDocsLinks,
                Set<LevelEnums> levelsOffered,
                Set<CurriculumEnums> curriculums) {


}