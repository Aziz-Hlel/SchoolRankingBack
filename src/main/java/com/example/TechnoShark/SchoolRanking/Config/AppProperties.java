package com.example.TechnoShark.SchoolRanking.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "app") // to help retreive values from application.yml under app attribute 
@Getter
@Setter
public class AppProperties {
    private String userId;
    private String schoolId;
}
