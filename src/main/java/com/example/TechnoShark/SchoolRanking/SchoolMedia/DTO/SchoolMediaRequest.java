package com.example.TechnoShark.SchoolRanking.SchoolMedia.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolMediaRequest {

    private String bqaReportLink;
    private String brochureLink;
    private String galleryLink;
    private String videoTourLink;

}
