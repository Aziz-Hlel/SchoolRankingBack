package com.example.TechnoShark.SchoolRanking.Seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// @Profile({"dev", "stage"})
@Profile("!test") // prevents seeding during unit tests, which is standard practice.
// @EventListener(ApplicationReadyEvent.class)
@Component
@RequiredArgsConstructor
@Slf4j
public class DataBaseSeeder implements CommandLineRunner {

    private final AdminSeeder adminSeeder;
    private final SchoolSeeder schoolSeeder;

    @Override
    public void run(String... args) throws Exception {

        log.info("Seeding database...");
        adminSeeder.seed();
        log.info("Super admins seeded.");
        schoolSeeder.seed();
        log.info("Schools seeded.");
        log.info("Database seeded.");

        // throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

}
