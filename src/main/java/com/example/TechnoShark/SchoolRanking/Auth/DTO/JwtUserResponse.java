package com.example.TechnoShark.SchoolRanking.Auth.DTO;

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
    private RoleEnums role;

    public String getUsername() { // * this function would add a dynamic field in the response called username
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
    }
}
