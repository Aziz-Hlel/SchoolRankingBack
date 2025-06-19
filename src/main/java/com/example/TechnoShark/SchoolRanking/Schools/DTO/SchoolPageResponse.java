package com.example.TechnoShark.SchoolRanking.Schools.DTO;

import java.util.UUID;

import com.example.TechnoShark.SchoolRanking.Enums.CountryEnums;

public record SchoolPageResponse(
        UUID id,
        String name,
        CountryEnums country,
        String city,
        String address,
        String phoneNumber,
        String email,
        String adminUsername,
        String website,
        boolean isComplete
) {

}
