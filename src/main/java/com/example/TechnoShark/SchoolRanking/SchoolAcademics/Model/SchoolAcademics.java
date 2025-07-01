package com.example.TechnoShark.SchoolRanking.SchoolAcademics.Model;

import java.util.Set;
import java.util.UUID;

import com.example.TechnoShark.SchoolRanking.Enums.AccreditationEnums;
import com.example.TechnoShark.SchoolRanking.Enums.CurriculumEnums;
import com.example.TechnoShark.SchoolRanking.Enums.LevelEnums;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Table(name = "school_academics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolAcademics {

    @Id
    private UUID id;

    @OneToOne
    @MapsId // Tells JPA to use the ID of the associated School as the ID of this entity
    @JoinColumn(name = "id") // Binds the foreign key to the id column
    private School school;

    private int languagesOfInstruction;

    @ElementCollection(targetClass = AccreditationEnums.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "school_accreditations", joinColumns = @JoinColumn(name = "school_id"))
    @Column(name = "accreditation")
    private Set<AccreditationEnums> internationalAccreditations;

    @Column(nullable = true)
    private String accreditationDocsLinks;

    // this is chat recommendation for the best approach for the case of multiple
    // values that are fixed and relativly small
    @ElementCollection(targetClass = LevelEnums.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "school_levels", joinColumns = @JoinColumn(name = "school_id"))
    @Column(name = "level")
    private Set<LevelEnums> levelsOffered;

    @ElementCollection(targetClass = CurriculumEnums.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "school_curriculums", joinColumns = @JoinColumn(name = "school_id"))
    @Column(name = "curriculum")
    private Set<CurriculumEnums> curriculums;
}
