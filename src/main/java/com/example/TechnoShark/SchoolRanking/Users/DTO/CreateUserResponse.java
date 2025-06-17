package com.example.TechnoShark.SchoolRanking.Users.DTO;

import java.util.UUID;

import com.example.TechnoShark.SchoolRanking.Enums.RoleEnums;
import com.example.TechnoShark.SchoolRanking.Schools.DTO.SchoolResponse;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;


public record CreateUserResponse(UUID id, String firstName, String lastName, String email, UUID schoolId, RoleEnums role) {
}