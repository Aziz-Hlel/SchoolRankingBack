package com.example.TechnoShark.SchoolRanking.Users.DTO;

import java.sql.Date;
import java.util.UUID;

import com.example.TechnoShark.SchoolRanking.Enums.RoleEnums;

public record UserPageResponse(
        UUID id,
        String username,
        String firstName,
        String lastName,
        RoleEnums role,
        String email,
        String schoolName,
        boolean isCompleted,
        Date createdAt) {
}
