package com.example.TechnoShark.SchoolRanking.Users.DTO;

import java.util.UUID;

import com.example.TechnoShark.SchoolRanking.Enums.RoleEnums;

public record UserResponse(UUID id, String firstName, String lastName, UUID schoolId, RoleEnums role, String email) {
}
