package com.example.TechnoShark.SchoolRanking.SchoolFacilities.Service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.TechnoShark.SchoolRanking.ErrorHandler.Exceptions.ResourceNotFoundException;
import com.example.TechnoShark.SchoolRanking.SchoolFacilities.DTO.SchoolFacilitiesRequest;
import com.example.TechnoShark.SchoolRanking.SchoolFacilities.DTO.SchoolFacilitiesResponse;
import com.example.TechnoShark.SchoolRanking.SchoolFacilities.Mapper.SchoolFacilitiesMapper;
import com.example.TechnoShark.SchoolRanking.SchoolFacilities.Model.SchoolFacilities;
import com.example.TechnoShark.SchoolRanking.SchoolFacilities.Repo.SchoolFacilitiesRepo;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;
import com.example.TechnoShark.SchoolRanking.Schools.Repo.SchoolRepo;
import com.example.TechnoShark.SchoolRanking.Schools.Service.FormProgressService;
import com.example.TechnoShark.SchoolRanking.Utils.CurrentProgressForm;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SchoolFacilitiesService {

        private final SchoolFacilitiesRepo schoolFacilitiesRepo;
        private final SchoolFacilitiesMapper schoolFacilitiesMapper;
        private final SchoolRepo schoolRepo;
        private final FormProgressService formProgressService;

        public UUID createSchoolFacilities(SchoolFacilitiesRequest schoolFacilitiesRequest, UUID schoolId) {

                School school = schoolRepo.findById(schoolId)
                                .orElseThrow(() -> new EntityNotFoundException("School not found"));

                SchoolFacilities entity = schoolFacilitiesMapper.toEntity(schoolFacilitiesRequest, school);

                SchoolFacilities savedEntity = schoolFacilitiesRepo.save(entity);

                formProgressService.updateFormProgress(schoolId, CurrentProgressForm.SCHOOL_STAFF);

                return savedEntity.getId();
        }

        public SchoolFacilitiesResponse updateSchoolFacilities(SchoolFacilitiesRequest schoolFacilitiesRequest,
                        UUID schoolId) {

                SchoolFacilities schoolFacilities = schoolFacilitiesRepo.findById(schoolId)
                                .orElseThrow(() -> new ResourceNotFoundException("School Facility not found"));
                schoolFacilitiesMapper.updateEntity(schoolFacilitiesRequest, schoolId,
                                schoolFacilities);

                SchoolFacilities updatedEntity = schoolFacilitiesRepo.save(schoolFacilities);

                SchoolFacilitiesResponse dto = schoolFacilitiesMapper.toDTO(updatedEntity);

                return dto;

        }

        public SchoolFacilitiesResponse getSchoolFacilities(UUID schoolId) {

                SchoolFacilities schoolFacilities = schoolFacilitiesRepo.findById(schoolId)
                                .orElseThrow(() -> new ResourceNotFoundException("School Facility not found"));

                SchoolFacilitiesResponse dto = schoolFacilitiesMapper.toDTO(schoolFacilities);

                return dto;

        }

}
