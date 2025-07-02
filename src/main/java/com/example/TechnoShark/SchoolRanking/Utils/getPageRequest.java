package com.example.TechnoShark.SchoolRanking.Utils;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class getPageRequest<T> {

    @Min(1)
    private int page = 1;

    @Min(1)
    @Max(100)
    private int size = 10;

    private List<String> sort = List.of();

    // You can even add filters here
    private String role;
    private String search;

}
