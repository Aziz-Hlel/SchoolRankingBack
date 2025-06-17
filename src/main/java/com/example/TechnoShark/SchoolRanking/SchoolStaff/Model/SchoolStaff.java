package com.example.TechnoShark.SchoolRanking.SchoolStaff.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.example.TechnoShark.SchoolRanking.Enums.Country;
import com.example.TechnoShark.SchoolRanking.Enums.Languages;
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

    @ElementCollection(targetClass = Country.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "school_staff_nationalities", joinColumns = @JoinColumn(name = "school_staff_id"))
    @Column(name = "country")
    private Set<Country> teacherNationalities;

    @ElementCollection(targetClass = Languages.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "school_staff_languages", joinColumns = @JoinColumn(name = "school_staff_id"))
    @Column(name = "language")
    private Set<Languages> teacherLanguages;

    @Column(nullable = true)
    private LocalDate lastInspectionDate;
}
