package com.example.TechnoShark.SchoolRanking.SchoolFacilities.DTO;

import java.util.List;
import java.util.UUID;

import com.example.TechnoShark.SchoolRanking.Enums.AccessibilityEnums;
import com.example.TechnoShark.SchoolRanking.Enums.FacilityEnums;
import com.example.TechnoShark.SchoolRanking.Enums.RatingLevelEnums;
import com.example.TechnoShark.SchoolRanking.Enums.SustainabilityEnums;

public record SchoolFacilitiesResponse(
        UUID id,
        List<FacilityEnums> facilities,
        List<AccessibilityEnums> accessibilityFeatures,
        List<SustainabilityEnums> sustainabilityPractices,
        List<String> universityDestinations,
        String csrActivities,
        List<String> industryPartnerships,
        boolean safetyCompliance,
        boolean aiIntegration,
        RatingLevelEnums technologyReadiness,
        String awardsAndRecognitions) {
}
