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

import com.example.TechnoShark.SchoolRanking.Config.AppProperties;
import com.example.TechnoShark.SchoolRanking.SchoolMedia.DTO.SchoolMediaResponse;
import com.example.TechnoShark.SchoolRanking.SchoolMedia.DTO.SchoolMediaRequest;
import com.example.TechnoShark.SchoolRanking.SchoolMedia.Service.SchoolMediaService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/school-media")
@AllArgsConstructor
public class SchoolMediaContoller {

    private final AppProperties appProperties;
    private final SchoolMediaService school_MediaService;

    @PostMapping({ "", "/" })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> create(@RequestBody @Valid SchoolMediaRequest school_MediaRequest) {
        String schoolId = appProperties.getSchoolId();
        String schoolId2 = school_MediaService.create(school_MediaRequest, schoolId);

        return ResponseEntity.status(HttpStatus.CREATED).body(schoolId2);
    }

    @PutMapping("/{schoolMediaId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> update(@PathVariable UUID schoolMediaId,
            @RequestBody @Valid SchoolMediaRequest school_MediaRequest) {
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
