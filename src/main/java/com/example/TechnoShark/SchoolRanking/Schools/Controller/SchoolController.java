package com.example.TechnoShark.SchoolRanking.Schools.Controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.TechnoShark.SchoolRanking.Auth.DTO.JwtUserResponse;
import com.example.TechnoShark.SchoolRanking.Auth.Util.UserContext;
import com.example.TechnoShark.SchoolRanking.Enums.RoleEnums;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolDetailedResponse2;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolPageRequest;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolPageResponse;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolProgressResponse;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolRequest;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolResponse;
import com.example.TechnoShark.SchoolRanking.Schools.Service.SchoolService;
import com.example.TechnoShark.SchoolRanking.Utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = { "/schools", "/school" })
@AllArgsConstructor
@Slf4j
public class SchoolController {

    private SchoolService schoolService;

    @PostMapping({ "", "/" })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> createSchool(@Valid @RequestBody SchoolRequest schoolRequest) {

        UUID userId = UserContext.getCurrentUserId();
        JwtUserResponse user = UserContext.getCurrentUser();
        if (user.getSchoolId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already has a school");
        }
        String schooldId = schoolService.create(schoolRequest, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(schooldId);
    }

    @PutMapping({ "/{schoolGeneralId}" })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SchoolResponse> updateSchool(@PathVariable UUID schoolGeneralId,
            @Valid @RequestBody SchoolRequest schoolRequest) {

        UUID userSchoolId = UserContext.getCurrentSchoolId();

        if (!schoolGeneralId.equals(userSchoolId) && UserContext.getRole() != RoleEnums.SUPER_ADMIN)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this school");

        SchoolResponse school = schoolService.update(schoolRequest, schoolGeneralId);

        return ResponseEntity.status(HttpStatus.OK).body(school);
    }

    @GetMapping({ "/{schoolId}" })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SchoolResponse> getSchool(@PathVariable UUID schoolId) {

        SchoolResponse school = schoolService.get(schoolId);
        return ResponseEntity.status(HttpStatus.OK).body(school);

    }

    @GetMapping({ "", "/" })
    public ResponseEntity<ApiResponse<Page<SchoolPageResponse>>> getPageSchool(
            @Valid @ModelAttribute SchoolPageRequest schoolPageRequest) {

        Pageable pageable = PageRequest.of(schoolPageRequest.getPage() - 1, schoolPageRequest.getSize());

        Page<SchoolPageResponse> schools = schoolService.getPage(pageable);

        ApiResponse<Page<SchoolPageResponse>> apiResponse = ApiResponse.<Page<SchoolPageResponse>>builder()
                .message("Schools retrieved successfully")
                .success(true)
                .data(schools)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/infos/{schoolId}")
    public ResponseEntity<ApiResponse<SchoolDetailedResponse2>> getDetailedSchool(@PathVariable UUID schoolId) {
        SchoolDetailedResponse2 school = schoolService.getDetailed(schoolId);

        ApiResponse<SchoolDetailedResponse2> apiResponse = ApiResponse.<SchoolDetailedResponse2>builder()
                .message("School retrieved successfully")
                .success(true)
                .data(school)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/form-progress")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<SchoolProgressResponse>> getFormProgress() {

        JwtUserResponse user = UserContext.getCurrentUser();

        UUID schoolId = user.getSchoolId();

        SchoolProgressResponse schoolProgressResponse = schoolService.getFormProgress(schoolId);

        ApiResponse<SchoolProgressResponse> apiResponse = ApiResponse.<SchoolProgressResponse>builder()
                .message("School form progress retrieved successfully")
                .success(true)
                .data(schoolProgressResponse)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

}
