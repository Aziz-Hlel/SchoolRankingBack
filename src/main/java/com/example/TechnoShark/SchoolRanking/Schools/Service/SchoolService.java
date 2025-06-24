package com.example.TechnoShark.SchoolRanking.Schools.Service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.TechnoShark.SchoolRanking.Auth.DTO.JwtUserResponse;
import com.example.TechnoShark.SchoolRanking.Enums.RoleEnums;
import com.example.TechnoShark.SchoolRanking.ErrorHandler.Exceptions.ResourceNotFoundException;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolDetailedResponse2;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolPageResponse;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolProgressResponse;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolRequest;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolResponse;
import com.example.TechnoShark.SchoolRanking.Schools.Mapper.SchoolMapper;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;
import com.example.TechnoShark.SchoolRanking.Schools.Repo.SchoolRepo;
import com.example.TechnoShark.SchoolRanking.Users.Model.User;
import com.example.TechnoShark.SchoolRanking.Users.Repo.UserRepo;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SchoolService {

    private SchoolRepo schoolRepo;
    private UserRepo userRepo;

    private final SchoolMapper schoolMapper;

    private final EntityManager entityManager;

    @Transactional
    public String create(SchoolRequest schoolRequest, UUID userId) {

        User user = entityManager.getReference(User.class, userId);

        School entity = schoolMapper.toEntity(schoolRequest, user);

        School school = schoolRepo.save(entity);

        user.setSchool(school);
        userRepo.save(user);
        return school.getId().toString();

    }

    @Transactional
    public SchoolResponse update(SchoolRequest schoolRequest, UUID schoolId, JwtUserResponse userId) {

        School existingSchool = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found"));

        if (!existingSchool.getUser().getId().equals(userId.getId()) && userId.getRole() != RoleEnums.ADMIN)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update this school");

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

    @Transactional
    public SchoolDetailedResponse2 getDetailed(UUID schoolId) {
        // ! add condition to check if user is the school's owner or he ADMIN
        School school = schoolRepo.findWithDetailsById(schoolId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found"));
        return schoolMapper.toDetailedDto2(school);
    }

    @Transactional
    public SchoolProgressResponse getFormProgress(UUID schoolId) {

        if (schoolId == null) {
            return new SchoolProgressResponse(false, 0);
        }

        School school = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));

        SchoolProgressResponse schoolProgressResponse = schoolMapper.toProgressDto(school);

        return schoolProgressResponse;
    }
}
