package com.example.TechnoShark.SchoolRanking.Schools.Service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.TechnoShark.SchoolRanking.ErrorHandler.Exceptions.ResourceNotFoundException;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolDetailedResponse2;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolPageResponse;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolProgressResponse;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolRequest;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolResponse;
import com.example.TechnoShark.SchoolRanking.Schools.Mapper.SchoolMapper;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;
import com.example.TechnoShark.SchoolRanking.Schools.Repo.SchoolRepo;
import com.example.TechnoShark.SchoolRanking.UserSchool.Model.UserSchool;
import com.example.TechnoShark.SchoolRanking.UserSchool.Repo.UserSchoolRepo;
import com.example.TechnoShark.SchoolRanking.Users.Model.User;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class SchoolService {

    private SchoolRepo schoolRepo;
    private final UserSchoolRepo userSchoolRepo;

    private final SchoolMapper schoolMapper;

    private final EntityManager entityManager;

    
    public String create(SchoolRequest schoolRequest, UUID userId) {

        User user = entityManager.getReference(User.class, userId);

        School entity = schoolMapper.toEntity(schoolRequest, user);

        School school = schoolRepo.save(entity);

        UserSchool userSchoolEntity = new UserSchool();

        userSchoolEntity.setUser(user);
        userSchoolEntity.setSchool(school);
        userSchoolRepo.save(userSchoolEntity);


        return school.getId().toString();

    }

    
    public SchoolResponse update(SchoolRequest schoolRequest, UUID schoolId) {

        School existingSchool = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found"));

        School updatedEntity = schoolMapper.updateSchoolFromDto(schoolRequest, existingSchool);

        School saved = schoolRepo.save(updatedEntity);

        return schoolMapper.toDto(saved);
    }

    public SchoolResponse get(UUID schoolId) {
        // ! add condition to check if user is the school's owner or he ADMIN
        School school = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found"));
        return schoolMapper.toDto(school);
    }

    @Transactional(readOnly = true)
    public Page<SchoolPageResponse> getPage(Pageable pageable) {

        Page<School> schools = schoolRepo.findAll(pageable);

        Page<SchoolPageResponse> page = schools.map(schoolMapper::toPageDto);

        return page;
    }

    
    public SchoolDetailedResponse2 getDetailed(UUID schoolId) {
        // ! add condition to check if user is the school's owner or he ADMIN
        School school = schoolRepo.findWithDetailsById(schoolId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found"));
        return schoolMapper.toDetailedDto2(school);
    }

    
    public SchoolProgressResponse getFormProgress(UUID schoolId) {

        School school = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));

        SchoolProgressResponse schoolProgressResponse = schoolMapper.toProgressDto(school);

        return schoolProgressResponse;
    }
}
