package com.example.TechnoShark.SchoolRanking.SchoolStaff.Service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TechnoShark.SchoolRanking.SchoolStaff.DTO.SchoolStaffRequestDTO;
import com.example.TechnoShark.SchoolRanking.SchoolStaff.DTO.SchoolStaffResponse;
import com.example.TechnoShark.SchoolRanking.SchoolStaff.Mapper.SchoolStaffMapper;
import com.example.TechnoShark.SchoolRanking.SchoolStaff.Model.SchoolStaff;
import com.example.TechnoShark.SchoolRanking.SchoolStaff.Repo.SchoolStaffRepo;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;
import com.example.TechnoShark.SchoolRanking.Schools.Repo.SchoolRepo;
import com.example.TechnoShark.SchoolRanking.Schools.Service.FormProgressService;
import com.example.TechnoShark.SchoolRanking.Utils.CurrentProgressForm;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SchoolStaffService {

    private SchoolStaffRepo school_StaffRepo;

    private SchoolStaffMapper school_StaffMapper;

    private SchoolRepo schoolRepo;

    private final FormProgressService formProgressService;

    public SchoolStaffResponse create(SchoolStaffRequestDTO dto, UUID schoolId) {

        Optional<School> school = schoolRepo.findById(schoolId);

        if (!school.isPresent()) // ! puisque 3ndk bidir relation walli a3ml condition ken School_StaffId != null
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "School Staff not found");

        SchoolStaff entity = school_StaffMapper.toEntity(dto, school.get());

        SchoolStaff savedSchool_Staff = school_StaffRepo.save(entity);

        formProgressService.updateFormProgress(schoolId, CurrentProgressForm.SCHOOL_MEDIA);

        return school_StaffMapper.toDTO(savedSchool_Staff);
    }

    public SchoolStaffResponse update(SchoolStaffRequestDTO dto, UUID schoolId) {

        Optional<SchoolStaff> school_Staff = school_StaffRepo.findById(schoolId);

        if (!school_Staff.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "School Staff not found");

        school_StaffMapper.UpdateEntity(dto, school_Staff.get());

        SchoolStaff updatedSchool_Staff = school_StaffRepo.save(school_Staff.get());

        return school_StaffMapper.toDTO(updatedSchool_Staff);

    }

    public SchoolStaffResponse get(UUID school_StaffId) {
        Optional<SchoolStaff> school_Staff = school_StaffRepo.findById(school_StaffId);
        if (!school_Staff.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "School Staff not found");
        return school_StaffMapper.toDTO(school_Staff.get());
    }
}
