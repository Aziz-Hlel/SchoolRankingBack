package com.example.TechnoShark.SchoolRanking.Schools.DTO;

import java.util.UUID;

public record MySchoolsPreview(
        UUID id,
        String name,
        boolean formsCompleted,
        int lastFormStep) {

}
