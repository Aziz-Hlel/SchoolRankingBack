package com.example.TechnoShark.SchoolRanking.SchoolMedia.Service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TechnoShark.SchoolRanking.SchoolMedia.DTO.SchoolMediaResponse;
import com.example.TechnoShark.SchoolRanking.SchoolMedia.DTO.SchoolMediaRequest;
import com.example.TechnoShark.SchoolRanking.SchoolMedia.Mapper.SchoolMediaMapper;
import com.example.TechnoShark.SchoolRanking.SchoolMedia.Model.SchoolMedia;
import com.example.TechnoShark.SchoolRanking.SchoolMedia.Repo.SchoolMediaRepo;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;
import com.example.TechnoShark.SchoolRanking.Schools.Repo.SchoolRepo;
import com.example.TechnoShark.SchoolRanking.Schools.Service.FormProgressService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SchoolMediaService {

    private final SchoolRepo schoolRepo;
    private final SchoolMediaRepo school_MediaRepo;
    private final SchoolMediaMapper school_MediaMapper;

    private final FormProgressService formProgressService;

    public UUID create(SchoolMediaRequest school_MediaRequest, UUID schoolId) {

        School school = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found"));

        SchoolMedia entity = school_MediaMapper.toEntity(school_MediaRequest, school);

        SchoolMedia schoolMedia = school_MediaRepo.save(entity);

        formProgressService.markFormsCompleted(school.getId());

        return schoolMedia.getId();

    }

    public String update(SchoolMediaRequest school_MediaRequest, UUID schoolMediaId) {
        Optional<SchoolMedia> schoolMedia = school_MediaRepo.findById(schoolMediaId);

        if (!schoolMedia.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "School Media not found");

        school_MediaMapper.updateEntity(school_MediaRequest, schoolMediaId, schoolMedia.get());

        SchoolMedia saved = school_MediaRepo.save(schoolMedia.get());
        return saved.getId().toString();
    }

    public SchoolMediaResponse get(UUID schoolMediaId) {
        SchoolMedia schoolMedia = school_MediaRepo.findById(schoolMediaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School Media not found"));
        return school_MediaMapper.toDto(schoolMedia);
    }
}
