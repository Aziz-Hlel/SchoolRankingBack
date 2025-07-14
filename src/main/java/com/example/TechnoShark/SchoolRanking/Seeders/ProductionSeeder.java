package com.example.TechnoShark.SchoolRanking.Seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Profile({ "prod", "stage" })
@Component
@RequiredArgsConstructor
@Slf4j
public class ProductionSeeder implements CommandLineRunner {

    private final AdminSeeder adminSeeder;

    @Override
    public void run(String... args) throws Exception {

        adminSeeder.createSuperAdmin("Ahmed", "Jdidi", "ajedidi@technoshark.org", "ajedidi");
        adminSeeder.createSuperAdmin("Aziz", "Hlel", "m.aziz.hlel@gmail.com", "XxXx9900");
        adminSeeder.createSuperAdmin("Abdalmuttaleb", "    ", "abdalmuttaleb@technoshark.org", "12345678");

    }

}
