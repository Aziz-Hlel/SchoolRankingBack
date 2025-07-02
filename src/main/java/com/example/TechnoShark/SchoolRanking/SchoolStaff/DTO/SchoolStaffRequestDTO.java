package com.example.TechnoShark.SchoolRanking.SchoolStaff.DTO;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.example.TechnoShark.SchoolRanking.Enums.CountryEnums;
import com.example.TechnoShark.SchoolRanking.Enums.LanguageEnums;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SchoolStaffRequestDTO {

    @NotBlank
    private String leadershipTeam;

    @NotBlank
    private String leadershipProfileLink;

    @Min(value = 1, message = "Staff size estimate must be at least 1")
    private int staffSizeEstimate;

    @NotBlank
    private String teacherQualifications;

    @NotNull(message = "Teacher nationalities must not be null")
    @Size(min = 1, message = "At least one teacher nationality must be provided") // Use @Size(min = 1) instead of
                                                                                  // @NotNull when you're checking for
                                                                                  // “must not be empty”

    private Set<CountryEnums> teacherNationalities =  new HashSet<>(); // Initialize List, Set, or Map fields when they’re
                                                                    // required or iterated over later

    @NotNull(message = "Teacher languages must not be null")
    @Size(min = 1, message = "At least one teacher language must be provided")
    private Set<LanguageEnums> teacherLanguages =  new HashSet<>();

    @NotBlank
    private String professionalDevelopment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Last inspection date cannot be in the future")
    @Nullable
    private LocalDate lastInspectionDate; // ! the format should be "YYYY-MM-DD" else it will throw a 400 mal JSON erro
                                          // , which is not very vague
}
