package com.example.TechnoShark.SchoolRanking.Schools.DTO;

import org.hibernate.validator.constraints.URL;

import com.example.TechnoShark.SchoolRanking.Enums.CountryEnums;
import com.example.TechnoShark.SchoolRanking.Enums.SchoolTypeEnums;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SchoolRequest {

    @NotBlank
    private String name;

    @NotNull
    private CountryEnums country;

    @NotBlank
    private String city;

    @NotBlank
    private String address;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Min(value = 1800, message = "Year must not be before 1800")
    @Max(value = 2025, message = "Year cannot be in the future")
    private Integer yearEstablished;

    @URL(protocol = "https", regexp = "^(https?://).*$", message = "Must be a valid URL starting with http:// or https://") // ! not mrigla
    @Nullable // You don’t need to explicitly say @Null because both @Pattern and @URL allow
          // null by default
    private String website;

    @NotNull
    private SchoolTypeEnums type;

}
