package com.example.TechnoShark.SchoolRanking.Schools.DTO;

import java.util.UUID;

import com.example.TechnoShark.SchoolRanking.Enums.CountryEnums;
import com.example.TechnoShark.SchoolRanking.Enums.SchoolTypeEnums;

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
    private SchoolTypeEnums type;
    private String website;
    private CountryEnums country;

}
