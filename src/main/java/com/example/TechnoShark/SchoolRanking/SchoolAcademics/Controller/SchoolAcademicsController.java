package com.example.TechnoShark.SchoolRanking.SchoolAcademics.Controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TechnoShark.SchoolRanking.Config.AppProperties;
import com.example.TechnoShark.SchoolRanking.SchoolAcademics.DTO.SchoolAcademicsRequest;
import com.example.TechnoShark.SchoolRanking.SchoolAcademics.DTO.SchoolAcademicsResponse;
import com.example.TechnoShark.SchoolRanking.SchoolAcademics.Service.SchoolAcademicsService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/school-academics")
@AllArgsConstructor
public class SchoolAcademicsController {

    private final AppProperties appProperties;
    private final SchoolAcademicsService school_AcademicsService;

    @PostMapping({ "", "/" })
    public ResponseEntity<UUID> create(@RequestBody @Valid SchoolAcademicsRequest school_AcademicsRequest) {
        UUID schoolId = UUID.fromString(appProperties.getSchoolId());
        UUID schoolAcademicsId = school_AcademicsService.create(school_AcademicsRequest, schoolId);
        return ResponseEntity.status(HttpStatus.CREATED).body(schoolAcademicsId);
    }

    @PutMapping({ "", "/" })
    public ResponseEntity<SchoolAcademicsResponse> update(
            @RequestBody @Valid SchoolAcademicsRequest school_AcademicsRequest) {
        UUID schoolId = UUID.fromString(appProperties.getSchoolId());
        SchoolAcademicsResponse updatedEntity = school_AcademicsService.update(school_AcademicsRequest, schoolId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEntity);
    }

    @GetMapping("/{schoolAcademicsId}")
    public ResponseEntity<SchoolAcademicsResponse> get(@PathVariable UUID schoolAcademicsId) {
        // UUID schoolId = UUID.fromString(appProperties.getSchoolId());
        SchoolAcademicsResponse schooldId = school_AcademicsService.get(schoolAcademicsId);
        return ResponseEntity.status(HttpStatus.OK).body(schooldId);
    }

}
