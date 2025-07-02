package com.example.TechnoShark.SchoolRanking.UserSchool.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.example.TechnoShark.SchoolRanking.Schools.Model.School;
import com.example.TechnoShark.SchoolRanking.UserSchool.Repo.UserSchoolRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchoolAuthorizationService {

    private final UserSchoolRepo userSchoolRepo;

    @Cacheable(value = "school-access", key = "#userId + '-' + #schoolId")
    public boolean canUserAccessSchool(UUID userId, UUID schoolId) {
        return userSchoolRepo.existsByUserIdAndSchoolId(userId, schoolId);
    }

    @Cacheable(value = "user-schools", key = "#userId")
    public List<School> getUserSchools(UUID userId) {
        return userSchoolRepo.findSchoolsByUserId(userId);
    }

    public void validateSchoolAccess(UUID userId, UUID schoolId) {
        if (!canUserAccessSchool(userId, schoolId)) {
            throw new AccessDeniedException("Access denied to school with ID: " + schoolId);
        }
    }
}
