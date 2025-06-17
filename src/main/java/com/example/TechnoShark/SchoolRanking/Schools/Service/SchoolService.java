package com.example.TechnoShark.SchoolRanking.Schools.Service;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolDetailedResponse;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolRequest;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolResponse;
import com.example.TechnoShark.SchoolRanking.Schools.Mapper.SchoolMapper;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;
import com.example.TechnoShark.SchoolRanking.Schools.Repo.SchoolRepo;
import com.example.TechnoShark.SchoolRanking.Users.Model.User;
import com.example.TechnoShark.SchoolRanking.Users.Repo.UserRepo;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SchoolService {

    private SchoolRepo schoolRepo;
    private UserRepo userRepo;

    private final SchoolMapper schoolMapper;

    private final EntityManager entityManager;

    public String create(SchoolRequest schoolRequest, UUID userId) {

        User user = entityManager.getReference(User.class, userId);

        School entity = schoolMapper.toEntity(schoolRequest, user);

        School school = schoolRepo.save(entity);

        user.setSchool(school);
        userRepo.save(user);
        return school.getId().toString();

    }

    public SchoolResponse update(SchoolRequest schoolRequest, UUID schoolId, UUID userId) {

        School existingSchool = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found"));

        if (!existingSchool.getUser().getId().toString().equals(userId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update this school");

        schoolMapper.updateSchoolFromDto(schoolRequest, schoolId, userId, existingSchool);

        School saved = schoolRepo.save(existingSchool);

        return schoolMapper.toDto(saved);
    }

    public SchoolResponse get(UUID schoolId) {
        // ! add condition to check if user is the school's owner or he ADMIN
        School school = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found"));
        return schoolMapper.toDto(school);
    }

    public SchoolDetailedResponse getDetailed(UUID schoolId) {
        // ! add condition to check if user is the school's owner or he ADMIN
        School school = schoolRepo.findWithDetailsById(schoolId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found"));
        return schoolMapper.toDetailedDto(school);
    }
}
