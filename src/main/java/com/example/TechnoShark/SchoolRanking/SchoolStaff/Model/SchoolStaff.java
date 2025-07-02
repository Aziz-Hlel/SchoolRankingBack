package com.example.TechnoShark.SchoolRanking.SchoolStaff.Model;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import com.example.TechnoShark.SchoolRanking.Enums.CountryEnums;
import com.example.TechnoShark.SchoolRanking.Enums.LanguageEnums;
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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "school_staff")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolStaff {

    @Id
    UUID id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    private School school;

    @Column(nullable = true)
    private String leadershipTeam;

    @Column(nullable = true)
    private String leadershipProfileLink;

    @Column(nullable = false)
    private int staffSizeEstimate;

    @Column(nullable = true)
    private String teacherQualifications;

    @Column(nullable = true)
    private String professionalDevelopment;

    @ElementCollection(targetClass = CountryEnums.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "school_staff_nationalities", joinColumns = @JoinColumn(name = "school_staff_id"))
    @Column(name = "country")
    private Set<CountryEnums> teacherNationalities;

    @ElementCollection(targetClass = LanguageEnums.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "school_staff_languages", joinColumns = @JoinColumn(name = "school_staff_id"))
    @Column(name = "language")
    private Set<LanguageEnums> teacherLanguages;

    @Column(nullable = true)
    private LocalDate lastInspectionDate;
}
