package com.example.TechnoShark.SchoolRanking.SchoolFacilities.DTO;

import java.util.List;

import com.example.TechnoShark.SchoolRanking.Enums.AccessibilityEnums;
import com.example.TechnoShark.SchoolRanking.Enums.FacilityEnums;
import com.example.TechnoShark.SchoolRanking.Enums.RatingLevel;
import com.example.TechnoShark.SchoolRanking.Enums.SustainabilityEnums;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolFacilitiesRequest {

    @NotNull
    private List<FacilityEnums> facilities;

    @NotNull
    private List<AccessibilityEnums> accessibilityFeatures;

    @NotNull
    private List<SustainabilityEnums> sustainabilityPractices;

    @NotNull
    private List<String> universityDestinations;

    @NotBlank
    private String csrActivities;

    private Boolean safetyCompliance;

    private Boolean aiIntegration;

    private RatingLevel technologyReadiness;

    @NotNull
    private List<String> industryPartnerships;

    private String awardsAndRecognitions;

}
