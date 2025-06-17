package com.example.TechnoShark.SchoolRanking.Users.DTO;

import java.util.UUID;

import com.example.TechnoShark.SchoolRanking.Enums.RoleEnums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtUserResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private UUID schoolId;
    private RoleEnums role;
}
