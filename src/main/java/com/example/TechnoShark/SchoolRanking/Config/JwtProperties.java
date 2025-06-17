package com.example.TechnoShark.SchoolRanking.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@ConfigurationProperties(prefix = "app.jwt")
@Data
@Validated // enables bean validation on this config class , without it the NotBlank &
           // NotNull annotations won't work and won't throw an error
public class JwtProperties {

    private static long oneMintue = 60 * 1000;
    private static long oneDay = oneMintue * 60 * 24;

    @NotBlank(message = "JWT secret must not be blank")
    @NotNull(message = "JWT secret must not be null")
    private String secret;

    private long accessTokenExpiration = 15 * oneMintue; // 15 minutes
    private long refreshTokenExpiration = 7 * oneDay; // 7 days
}
