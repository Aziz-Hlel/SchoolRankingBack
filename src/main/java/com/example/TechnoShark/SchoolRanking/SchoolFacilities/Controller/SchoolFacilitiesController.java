package com.example.TechnoShark.SchoolRanking.SchoolFacilities.Controller;

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
import com.example.TechnoShark.SchoolRanking.SchoolFacilities.DTO.SchoolFacilitiesRequest;
import com.example.TechnoShark.SchoolRanking.SchoolFacilities.DTO.SchoolFacilitiesResponse;
import com.example.TechnoShark.SchoolRanking.SchoolFacilities.Service.SchoolFacilitiesService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/school-facilities")
@AllArgsConstructor
public class SchoolFacilitiesController {

    private final AppProperties appProperties;
    private final SchoolFacilitiesService schoolFacilitiesService;

    @PostMapping({ "", "/" })
    public ResponseEntity<UUID> post(@Valid @RequestBody SchoolFacilitiesRequest schoolFacilitiesRequest) {

        UUID schoolId = UUID.fromString(appProperties.getSchoolId());

        UUID schoolFacilitiesId = schoolFacilitiesService.createSchoolFacilities(schoolFacilitiesRequest, schoolId);

        return ResponseEntity.status(HttpStatus.CREATED).body(schoolFacilitiesId);
    }

    @PutMapping("/{schoolFacilitiesId}")
    public ResponseEntity<SchoolFacilitiesResponse> put(@PathVariable UUID schoolFacilitiesId,
            @Valid @RequestBody SchoolFacilitiesRequest schoolFacilitiesRequest) {

        UUID schoolId = UUID.fromString(appProperties.getSchoolId());

        SchoolFacilitiesResponse updatedEntity = schoolFacilitiesService.updateSchoolFacilities(schoolFacilitiesRequest,
                schoolId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedEntity);
    }

    @GetMapping("/{schoolFacilitiesId}")
    public ResponseEntity<SchoolFacilitiesResponse> get(@PathVariable UUID schoolFacilitiesId) {

        SchoolFacilitiesResponse entity = schoolFacilitiesService.getSchoolFacilities(schoolFacilitiesId);

        return ResponseEntity.status(HttpStatus.OK).body(entity);
    }

}
