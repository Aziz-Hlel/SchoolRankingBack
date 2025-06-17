package com.example.TechnoShark.SchoolRanking.Schools.DTO;

import java.util.UUID;

import com.example.TechnoShark.SchoolRanking.Enums.Country;
import com.example.TechnoShark.SchoolRanking.Enums.SchoolType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolResponse {
    private UUID id;
    private String name;
    private String city;
    private String address;
    private String phoneNumber;
    private String email;
    private Integer yearEstablished;
    private SchoolType type;
    private String website;
    private Country country;

}
