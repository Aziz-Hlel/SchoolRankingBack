package com.example.TechnoShark.SchoolRanking.Auth.Util;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.TechnoShark.SchoolRanking.Auth.DTO.JwtUserResponse;
import com.example.TechnoShark.SchoolRanking.Auth.Model.CustomUserDetails;
import com.example.TechnoShark.SchoolRanking.Enums.RoleEnums;

public class UserContext {

    private UserContext() {
        // Prevent instantiation
    }

    public static CustomUserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return (CustomUserDetails) authentication.getPrincipal();
        }
        throw new IllegalStateException("No authenticated user found or wrong principal type");
    }

    public static JwtUserResponse getCurrentUser() {
        return getCurrentUserDetails().getUser();
    }

    public static UUID getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public static RoleEnums getRole() {
        return getCurrentUser().getRole();
    }

}
