package com.example.TechnoShark.SchoolRanking.Seeders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.TechnoShark.SchoolRanking.Enums.AccessibilityEnums;
import com.example.TechnoShark.SchoolRanking.Enums.AccreditationEnums;
import com.example.TechnoShark.SchoolRanking.Enums.Country;
import com.example.TechnoShark.SchoolRanking.Enums.CurriculumEnums;
import com.example.TechnoShark.SchoolRanking.Enums.FacilityEnums;
import com.example.TechnoShark.SchoolRanking.Enums.Languages;
import com.example.TechnoShark.SchoolRanking.Enums.LevelsEnums;
import com.example.TechnoShark.SchoolRanking.Enums.RatingLevel;
import com.example.TechnoShark.SchoolRanking.Enums.RoleEnums;
import com.example.TechnoShark.SchoolRanking.Enums.SchoolType;
import com.example.TechnoShark.SchoolRanking.Enums.SustainabilityEnums;
import com.example.TechnoShark.SchoolRanking.SchoolAcademics.Model.SchoolAcademics;
import com.example.TechnoShark.SchoolRanking.SchoolAcademics.Repo.SchoolAcademicsRepo;
import com.example.TechnoShark.SchoolRanking.SchoolFacilities.Model.SchoolFacilities;
import com.example.TechnoShark.SchoolRanking.SchoolFacilities.Repo.SchoolFacilitiesRepo;
import com.example.TechnoShark.SchoolRanking.SchoolMedia.Model.SchoolMedia;
import com.example.TechnoShark.SchoolRanking.SchoolMedia.Repo.SchoolMediaRepo;
import com.example.TechnoShark.SchoolRanking.SchoolStaff.Model.SchoolStaff;
import com.example.TechnoShark.SchoolRanking.SchoolStaff.Repo.SchoolStaffRepo;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;
import com.example.TechnoShark.SchoolRanking.Schools.Repo.SchoolRepo;
import com.example.TechnoShark.SchoolRanking.Users.Model.User;
import com.example.TechnoShark.SchoolRanking.Users.Repo.UserRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchoolSeeder {
    private final UserRepo userRepo;
    private final SchoolRepo schoolRepo;
    private final PasswordEncoder passwordEncoder;

    private final int numberOfSeeds = 3;

    public int getNumberofLanguagesOfInstruction() {
        return ThreadLocalRandom.current().nextInt(1, 10);

    }

    public int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public boolean getRandomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    public <E extends Enum<E>> E getRandomEnumValue(Class<E> enumClass) {

        E[] values = enumClass.getEnumConstants();

        return values[ThreadLocalRandom.current().nextInt(values.length)];
    }

    private <E extends Enum<E>> Set<E> getRandomEnumSet(Class<E> enumClass) {

        E[] values = enumClass.getEnumConstants();
        int length = values.length;

        List<E> shuffled = new ArrayList<>(List.of(values));
        Collections.shuffle(shuffled);
        int count = ThreadLocalRandom.current().nextInt(1, length + 1);

        return new HashSet<>(shuffled.subList(0, count));

    }

    private LocalDate getRandomLocalDate(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    @Transactional
    public void seed() {

        if (schoolRepo.count() != 0)
            return;

        for (int i = 1; i <= numberOfSeeds; i++) {

            // --- 2. Create School ---
            School school = new School();
            school.setName("school" + i);
            school.setCountry(getRandomEnumValue(Country.class));
            school.setCity("school" + i);
            school.setAddress("school" + i + " address");
            school.setPhoneNumber("0000000" + i);
            school.setEmail("school" + i + "@example.com");
            school.setYearEstablished(2000 + i);
            school.setType(getRandomEnumValue(SchoolType.class));
            school.setWebsite("https://school" + i + ".tn");

            // --- 3. Create Academics BEFORE saving school ---
            SchoolAcademics academics = new SchoolAcademics();
            academics.setSchool(school); // Set the school reference
            academics.setAccreditationDocsLinks("school" + i + ".com/accreditationDocsLinks");
            academics.setLanguagesOfInstruction(getNumberofLanguagesOfInstruction());
            academics.setCurriculums(getRandomEnumSet(CurriculumEnums.class));
            academics.setInternationalAccreditations(getRandomEnumSet(AccreditationEnums.class));
            academics.setLevelsOffered(getRandomEnumSet(LevelsEnums.class));

            // Set the relationship on both sides
            school.setSchoolAcademics(academics);

            // --- 5. Save ONLY the User (it will cascade to save School and Academics) ---

            SchoolFacilities facilities = new SchoolFacilities();
            facilities.setAccessibilityFeatures(getRandomEnumSet(AccessibilityEnums.class));
            facilities.setFacilities(getRandomEnumSet(FacilityEnums.class));
            facilities.setSustainabilityPractices(getRandomEnumSet(SustainabilityEnums.class));
            facilities.setUniversityDestinations(Set.of("university destination 1",
                    "university destination 2"));
            facilities.setCsrActivities("school" + i + " csr activities");
            facilities.setIndustryPartnerships(Set.of("industry partnership 1", "industry partnership 2"));
            facilities.setSafetyCompliance(getRandomBoolean());
            facilities.setAiIntegration(getRandomBoolean());
            facilities.setTechnologyReadiness(getRandomEnumValue(RatingLevel.class));
            facilities.setAwardsAndRecognitions("school" + i + " awards and recognitions");

            facilities.setSchool(school);
            school.setSchoolFacilities(facilities);

            SchoolMedia media = new SchoolMedia();
            media.setBqaReportLink("https://school" + i + ".com/bqaReportLink");
            media.setBrochureLink("https://school\" + i + \".com/BrochureLink");
            media.setGalleryLink("https://school" + i + ".com/GalleryLink");
            media.setVideoTourLink("https://school" + i + ".com/VideoTourLink");

            media.setSchool(school);
            school.setSchoolMedia(media);

            SchoolStaff staff = new SchoolStaff();
            staff.setLeadershipProfileLink("https://school" + i + ".com/LeadershipProfileLink");
            staff.setLeadershipTeam("school" + i + " leadership team");
            staff.setProfessionalDevelopment("school" + i + " professional development");
            staff.setStaffSizeEstimate(getRandomNumber(5, 50));
            staff.setTeacherLanguages(getRandomEnumSet(Languages.class));
            staff.setTeacherNationalities(getRandomEnumSet(Country.class));
            staff.setTeacherQualifications("school" + i + " teacher qualifications");
            staff.setLastInspectionDate(getRandomLocalDate(LocalDate.of(2000, 1, 1), LocalDate.of(2025, 1, 1)));

            staff.setSchool(school);
            school.setSchoolStaff(staff);

            // --- 4. Create User ---
            User user = User.builder()
                    .firstName("Admin")
                    .lastName(String.valueOf(i))
                    .email("admin" + i + "@example.com")
                    .password(passwordEncoder.encode("admin" + i))
                    .role(RoleEnums.ADMIN)
                    .school(school) // Set school reference
                    .build();

            school.setUser(user);

            user = userRepo.save(user);

            log.info("School created with id: {}", user.getSchool().getSchoolAcademics().getId().toString());
        }

    }

}
