package com.example.TechnoShark.SchoolRanking.SchoolMedia.Model;

import java.util.UUID;

import com.example.TechnoShark.SchoolRanking.Schools.Model.School;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "school_media")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// ! there's some dependency to be added for this one below
// @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class SchoolMedia {

    @Id
    private UUID id;

    @OneToOne
    @MapsId // Tells JPA to use the ID of the associated School as the ID of this entity
    @JoinColumn(name = "id") // Binds the foreign key to the id column
    private School school;

    @Column(nullable = true)
    private String bqaReportLink;

    @Column(nullable = true)
    private String brochureLink;

    @Column(nullable = true)
    private String galleryLink;

    @Column(nullable = true)
    private String videoTourLink;
    // !
    // @Type(type = "jsonb")
    // @Column(name = "other_documents_links", columnDefinition = "jsonb")
    // private Map<String, String> otherDocumentsLinks;
}
