package com.example.TechnoShark.SchoolRanking.Users.DTO;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class getUsersPageRequest {

    @Min(1)
    private int page = 1;

    @Min(1)
    @Max(100)
    private int size = 10;

    private List<String> sort = List.of("id,asc");

    // You can even add filters here
    private String role;
    private String search;

}
