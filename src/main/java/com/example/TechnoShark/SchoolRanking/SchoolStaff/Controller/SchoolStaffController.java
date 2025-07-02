package com.example.TechnoShark.SchoolRanking.SchoolStaff.Controller;

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
import com.example.TechnoShark.SchoolRanking.SchoolStaff.DTO.SchoolStaffRequestDTO;
import com.example.TechnoShark.SchoolRanking.SchoolStaff.DTO.SchoolStaffResponse;
import com.example.TechnoShark.SchoolRanking.SchoolStaff.Service.SchoolStaffService;
import com.example.TechnoShark.SchoolRanking.UserSchool.Service.SchoolAuthorizationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/schools/{schoolId}/school-staff")
@AllArgsConstructor
public class SchoolStaffController {

    private SchoolStaffService school_StaffService;
    private final SchoolAuthorizationService schoolAuthService;

    @PostMapping({ "", "/" })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SchoolStaffResponse> create(
            @PathVariable UUID schoolId,
            @RequestBody @Valid SchoolStaffRequestDTO school_StaffRequestDTO) {

        UUID userId = UserContext.getCurrentUserId();

        if (!schoolAuthService.canUserAccessSchool(userId, schoolId) && UserContext.getRole() != RoleEnums.SUPER_ADMIN)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this school");

        SchoolStaffResponse response = school_StaffService.create(school_StaffRequestDTO, schoolId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{schoolFacilitiesId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SchoolStaffResponse> update(
            @PathVariable UUID schoolId,
            @PathVariable UUID schoolFacilitiesId,
            @RequestBody @Valid SchoolStaffRequestDTO school_StaffRequestDTO) {

        UUID userId = UserContext.getCurrentUserId();

        if (!schoolAuthService.canUserAccessSchool(userId, schoolId) && UserContext.getRole() != RoleEnums.SUPER_ADMIN)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this school");

        SchoolStaffResponse response = school_StaffService.update(school_StaffRequestDTO, schoolFacilitiesId);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/{school_StaffId}")
    public ResponseEntity<SchoolStaffResponse> get(@PathVariable UUID school_StaffId) {
        SchoolStaffResponse schooldId = school_StaffService.get(school_StaffId);
        return ResponseEntity.status(HttpStatus.OK).body(schooldId);
    }

}
