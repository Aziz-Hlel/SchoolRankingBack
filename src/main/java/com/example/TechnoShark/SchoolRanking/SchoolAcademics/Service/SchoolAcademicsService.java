package com.example.TechnoShark.SchoolRanking.SchoolAcademics.Service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TechnoShark.SchoolRanking.SchoolAcademics.DTO.SchoolAcademicsRequest;
import com.example.TechnoShark.SchoolRanking.SchoolAcademics.DTO.SchoolAcademicsResponse;
import com.example.TechnoShark.SchoolRanking.SchoolAcademics.Mapper.SchoolAcademicsMapper;
import com.example.TechnoShark.SchoolRanking.SchoolAcademics.Model.SchoolAcademics;
import com.example.TechnoShark.SchoolRanking.SchoolAcademics.Repo.SchoolAcademicsRepo;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;
import com.example.TechnoShark.SchoolRanking.Schools.Repo.SchoolRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SchoolAcademicsService {

    private SchoolAcademicsRepo school_AcademicsRepo;
    private SchoolAcademicsMapper school_AcademicsMapper;
    private SchoolRepo schoolRepo;

    public UUID create(SchoolAcademicsRequest school_AcademicsRequest, UUID schooldId) {

        Optional<School> school = schoolRepo.findById(schooldId);

        if (!school.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found");
        SchoolAcademics newEntity = school_AcademicsMapper.toEntity(school_AcademicsRequest, school.get());

        SchoolAcademics savedEntity = school_AcademicsRepo.save(newEntity);

        return savedEntity.getId();
    }

    public SchoolAcademicsResponse update(SchoolAcademicsRequest school_AcademicsRequest, UUID schooldId) {
        Optional<SchoolAcademics> schoolAcedemics = school_AcademicsRepo.findById(schooldId);

        if (!schoolAcedemics.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "School Acedemics not found");

        SchoolAcademics updatedEntity = school_AcademicsMapper.updateEntity(school_AcademicsRequest, schooldId,
                schoolAcedemics.get());

        SchoolAcademics savedEntity = school_AcademicsRepo.save(updatedEntity);

        return school_AcademicsMapper.toDto(savedEntity);
    }

    public SchoolAcademicsResponse get(UUID schoolId) {
        Optional<SchoolAcademics> school_Academics = school_AcademicsRepo.findById(schoolId);

        if (school_Academics.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "School Acedemics not found");

        return school_AcademicsMapper.toDto(school_Academics.get());

    }
    
}
