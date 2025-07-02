package com.example.TechnoShark.SchoolRanking.SchoolAcademics.Controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.TechnoShark.SchoolRanking.Auth.Util.UserContext;
import com.example.TechnoShark.SchoolRanking.Enums.RoleEnums;
import com.example.TechnoShark.SchoolRanking.SchoolAcademics.DTO.SchoolAcademicsRequest;
import com.example.TechnoShark.SchoolRanking.SchoolAcademics.DTO.SchoolAcademicsResponse;
import com.example.TechnoShark.SchoolRanking.SchoolAcademics.Service.SchoolAcademicsService;
import com.example.TechnoShark.SchoolRanking.UserSchool.Service.SchoolAuthorizationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/schools/{schoolId}/school-academics")
@AllArgsConstructor
public class SchoolAcademicsController {

    private final SchoolAcademicsService school_AcademicsService;
    private final SchoolAuthorizationService schoolAuthService;

    @PostMapping({ "", "/" })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UUID> create(@PathVariable UUID schoolId,
            @RequestBody @Valid SchoolAcademicsRequest school_AcademicsRequest) {

        UUID userId = UserContext.getCurrentUserId();

        if (!schoolAuthService.canUserAccessSchool(userId, schoolId) && UserContext.getRole() != RoleEnums.SUPER_ADMIN)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this school");

        UUID schoolAcademicsId = school_AcademicsService.create(school_AcademicsRequest, schoolId);

        return ResponseEntity.status(HttpStatus.CREATED).body(schoolAcademicsId);
    }

    @PutMapping("/{schoolAcademicsId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SchoolAcademicsResponse> update(
            @PathVariable UUID schoolId,
            @PathVariable UUID schoolAcademicsId,
            @RequestBody @Valid SchoolAcademicsRequest school_AcademicsRequest) {

        UUID userId = UserContext.getCurrentUserId();

        if (!schoolAuthService.canUserAccessSchool(userId, schoolId) && UserContext.getRole() != RoleEnums.SUPER_ADMIN)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this school");

        SchoolAcademicsResponse updatedEntity = school_AcademicsService.update(school_AcademicsRequest,
                schoolAcademicsId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedEntity);
    }

    @GetMapping("/{schoolAcademicsId}")
    public ResponseEntity<SchoolAcademicsResponse> get(@PathVariable UUID schoolAcademicsId) {
        // UUID schoolId = UserContext.getCurrentSchoolId();

        SchoolAcademicsResponse schooldId = school_AcademicsService.get(schoolAcademicsId);

        return ResponseEntity.status(HttpStatus.OK).body(schooldId);

    }

}
