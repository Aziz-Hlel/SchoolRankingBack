package com.example.TechnoShark.SchoolRanking.Users.DTO;

import java.sql.Date;
import java.util.UUID;

import com.example.TechnoShark.SchoolRanking.Enums.RoleEnums;

// TODO: add nbr of schools , nbr of schools comlpleted, schools name , you figuire out l8er
public record UserPageResponse(
                UUID id,
                String username,
                String firstName,
                String lastName,
                RoleEnums role,
                String email,
                Date createdAt) {
}
