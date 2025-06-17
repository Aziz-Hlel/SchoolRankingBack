package com.example.TechnoShark.SchoolRanking.Schools.Controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TechnoShark.SchoolRanking.Auth.Model.CustomUserDetails;
import com.example.TechnoShark.SchoolRanking.Auth.Util.UserContext;
import com.example.TechnoShark.SchoolRanking.Config.AppProperties;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolDetailedResponse;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolRequest;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolResponse;
import com.example.TechnoShark.SchoolRanking.Schools.Service.SchoolService;

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

        String schooldId = schoolService.create(schoolRequest, userId);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(schooldId);
    }

    @PutMapping({ "/{schoolId}" })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SchoolResponse> updateSchool(@PathVariable UUID schoolId,
            @Valid @RequestBody SchoolRequest schoolRequest) {

        UUID userId = UserContext.getCurrentUserId();

        SchoolResponse school = schoolService.update(schoolRequest, schoolId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(school);
    }

    @GetMapping({ "/{schoolId}" })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SchoolResponse> getSchool(@PathVariable UUID schoolId) {

        SchoolResponse school = schoolService.get(schoolId);
        return ResponseEntity.status(HttpStatus.OK).body(school);

    }

    @GetMapping("/infos/{schoolId}")
    public ResponseEntity<SchoolDetailedResponse> getDetailedSchool(@PathVariable UUID schoolId) {
        SchoolDetailedResponse school = schoolService.getDetailed(schoolId);
        return ResponseEntity.status(HttpStatus.OK).body(school);
    }

}
