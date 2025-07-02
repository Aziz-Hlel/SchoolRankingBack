package com.example.TechnoShark.SchoolRanking.Schools.DTO;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class SchoolPageRequest {

    @Min(1)
    private int page = 1;

    @Min(1)
    @Max(100)
    private int size = 10;


    // You can even add filters here
    private String role;
    private String search;

}
