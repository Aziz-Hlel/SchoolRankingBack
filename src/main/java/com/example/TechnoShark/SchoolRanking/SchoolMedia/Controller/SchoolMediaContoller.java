package com.example.TechnoShark.SchoolRanking.SchoolMedia.Controller;

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
import com.example.TechnoShark.SchoolRanking.SchoolMedia.DTO.SchoolMediaResponse;
import com.example.TechnoShark.SchoolRanking.SchoolMedia.DTO.SchoolMediaRequest;
import com.example.TechnoShark.SchoolRanking.SchoolMedia.Service.SchoolMediaService;
import com.example.TechnoShark.SchoolRanking.UserSchool.Service.SchoolAuthorizationService;
import com.example.TechnoShark.SchoolRanking.Utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/schools/{schoolId}/school-media")
@AllArgsConstructor
public class SchoolMediaContoller {

    private final SchoolMediaService school_MediaService;
    private final SchoolAuthorizationService schoolAuthService;

    @PostMapping({ "", "/" })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UUID>> create(
            @PathVariable UUID schoolId,
            @RequestBody @Valid SchoolMediaRequest school_MediaRequest) {

        UUID userId = UserContext.getCurrentUserId();

        if (!schoolAuthService.canUserAccessSchool(userId, schoolId) && UserContext.getRole() != RoleEnums.SUPER_ADMIN)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this school");

        UUID schoolMediaId = school_MediaService.create(school_MediaRequest, schoolId);

        ApiResponse<UUID> apiResponse = ApiResponse.<UUID>builder()
                .message("School media created successfully")
                .success(true)
                .data(schoolMediaId)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{schoolMediaId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> update(
            @PathVariable UUID schoolId,
            @PathVariable UUID schoolMediaId,
            @RequestBody @Valid SchoolMediaRequest school_MediaRequest) {

        UUID userId = UserContext.getCurrentUserId();

        if (!schoolAuthService.canUserAccessSchool(userId, schoolId) && UserContext.getRole() != RoleEnums.SUPER_ADMIN)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this school");

        String schooldId = school_MediaService.update(school_MediaRequest, schoolMediaId);
        return ResponseEntity.status(HttpStatus.OK).body(schooldId);
    }

    @GetMapping("/{schoolMediaId}")
    public ResponseEntity<SchoolMediaResponse> get(@PathVariable UUID schoolMediaId) {
        SchoolMediaResponse schooldId = school_MediaService.get(schoolMediaId);
        return ResponseEntity.status(HttpStatus.OK).body(schooldId);
    }
    // String schoolMdediaId = appProperties.getSchoolId();
}
