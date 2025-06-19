package com.example.TechnoShark.SchoolRanking.Auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private String accessToken;

    private String refreshToken;

    private JwtUserResponse user;

    @Builder.Default
    private String tokenType = "Bearer";

    private Long expiresIn;
}