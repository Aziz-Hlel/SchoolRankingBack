package com.example.TechnoShark.SchoolRanking.SchoolFacilities.Controller;

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
import com.example.TechnoShark.SchoolRanking.SchoolFacilities.DTO.SchoolFacilitiesRequest;
import com.example.TechnoShark.SchoolRanking.SchoolFacilities.DTO.SchoolFacilitiesResponse;
import com.example.TechnoShark.SchoolRanking.SchoolFacilities.Service.SchoolFacilitiesService;
import com.example.TechnoShark.SchoolRanking.UserSchool.Service.SchoolAuthorizationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/schools/{schoolId}/school-facilities")
@AllArgsConstructor
public class SchoolFacilitiesController {

    private final SchoolFacilitiesService schoolFacilitiesService;
    private final SchoolAuthorizationService schoolAuthService;

    @PostMapping({ "", "/" })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UUID> post(
            @PathVariable UUID schoolId,
            @Valid @RequestBody SchoolFacilitiesRequest schoolFacilitiesRequest) {

        UUID userId = UserContext.getCurrentUserId();

        if (!schoolAuthService.canUserAccessSchool(userId, schoolId) && UserContext.getRole() != RoleEnums.SUPER_ADMIN)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this school");

        UUID schoolFacilitiesId = schoolFacilitiesService.createSchoolFacilities(schoolFacilitiesRequest, schoolId);

        return ResponseEntity.status(HttpStatus.CREATED).body(schoolFacilitiesId);

    }

    @PutMapping("/{schoolFacilitiesId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SchoolFacilitiesResponse> put(
            @PathVariable UUID schoolId,
            @PathVariable UUID schoolFacilitiesId,
            @Valid @RequestBody SchoolFacilitiesRequest schoolFacilitiesRequest) {

        UUID userId = UserContext.getCurrentUserId();

        if (!schoolAuthService.canUserAccessSchool(userId, schoolId) && UserContext.getRole() != RoleEnums.SUPER_ADMIN)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this school");

        SchoolFacilitiesResponse updatedEntity = schoolFacilitiesService.updateSchoolFacilities(schoolFacilitiesRequest,
                schoolFacilitiesId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedEntity);
    }

    @GetMapping("/{schoolFacilitiesId}")
    public ResponseEntity<SchoolFacilitiesResponse> get(@PathVariable UUID schoolFacilitiesId) {

        SchoolFacilitiesResponse entity = schoolFacilitiesService.getSchoolFacilities(schoolFacilitiesId);

        return ResponseEntity.status(HttpStatus.OK).body(entity);
    }

}
