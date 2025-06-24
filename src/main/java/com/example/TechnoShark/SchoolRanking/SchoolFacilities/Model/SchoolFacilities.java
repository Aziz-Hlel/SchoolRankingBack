package com.example.TechnoShark.SchoolRanking.SchoolFacilities.Model;

import java.util.Set;
import java.util.UUID;

import com.example.TechnoShark.SchoolRanking.Enums.AccessibilityEnums;
import com.example.TechnoShark.SchoolRanking.Enums.FacilityEnums;
import com.example.TechnoShark.SchoolRanking.Enums.RatingLevelEnums;
import com.example.TechnoShark.SchoolRanking.Enums.SustainabilityEnums;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "school_facilities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolFacilities {

    @Id
    private UUID id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    private School school;

    @ElementCollection(targetClass = FacilityEnums.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "facilities", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "facility")
    private Set<FacilityEnums> facilities;

    @ElementCollection(targetClass = AccessibilityEnums.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "accessibility_features", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "accessibility_feature")
    private Set<AccessibilityEnums> accessibilityFeatures;

    @ElementCollection(targetClass = SustainabilityEnums.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "sustainability_practices", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "sustainability_practice")
    private Set<SustainabilityEnums> sustainabilityPractices;

    @ElementCollection
    @CollectionTable(name = "university_destinations", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "university_destination")
    private Set<String> universityDestinations;

    private String csrActivities;

    @ElementCollection
    @CollectionTable(name = "industry_partnerships", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "industry_partnership")
    private Set<String> industryPartnerships;

    @Column(nullable = false)
    private Boolean safetyCompliance;

    @Column(nullable = false)
    private Boolean aiIntegration;

    @Enumerated(EnumType.STRING)
    private RatingLevelEnums technologyReadiness;

    private String awardsAndRecognitions;
}
