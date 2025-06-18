package com.example.TechnoShark.SchoolRanking.Users.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {

    private String firstName;
    private String lastName;
    private String email;
}
