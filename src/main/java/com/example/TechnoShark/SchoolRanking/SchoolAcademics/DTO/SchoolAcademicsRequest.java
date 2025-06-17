package com.example.TechnoShark.SchoolRanking.SchoolAcademics.DTO;

import java.util.Set;

import com.example.TechnoShark.SchoolRanking.Enums.AccreditationEnums;
import com.example.TechnoShark.SchoolRanking.Enums.CurriculumEnums;
import com.example.TechnoShark.SchoolRanking.Enums.LevelsEnums;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SchoolAcademicsRequest {

    @NotNull
    private int languagesOfInstruction;

    @NotNull
    private Set<AccreditationEnums> internationalAccreditations;

    @NotBlank
    private String accreditationDocsLinks;

    @NotNull
    private Set<LevelsEnums> levelsOffered;

    @NotNull
    private Set<CurriculumEnums> curriculums;

}
