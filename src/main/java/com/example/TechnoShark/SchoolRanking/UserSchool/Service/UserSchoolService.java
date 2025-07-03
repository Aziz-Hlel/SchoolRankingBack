package com.example.TechnoShark.SchoolRanking.UserSchool.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.TechnoShark.SchoolRanking.Schools.DTO.MySchoolsPreview;
import com.example.TechnoShark.SchoolRanking.Schools.Mapper.SchoolMapper;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;
import com.example.TechnoShark.SchoolRanking.UserSchool.Repo.UserSchoolRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSchoolService {

    private final UserSchoolRepo userSchoolRepo;

    private final SchoolMapper schoolMapper;

    public List<MySchoolsPreview> getUserSchools(UUID userId) {

        List<School> userSchools = userSchoolRepo.findSchoolsByUserId(userId);

        List<MySchoolsPreview> userSchoolsDto = schoolMapper.toSideBarSchoolDto(userSchools);

        return userSchoolsDto;

    }

}
