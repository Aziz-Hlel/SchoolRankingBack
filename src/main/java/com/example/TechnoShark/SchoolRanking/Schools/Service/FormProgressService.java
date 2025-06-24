package com.example.TechnoShark.SchoolRanking.Schools.Service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.TechnoShark.SchoolRanking.Schools.Model.School;
import com.example.TechnoShark.SchoolRanking.Schools.Repo.SchoolRepo;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FormProgressService {

    private final SchoolRepo schoolRepository;

    /**
     * Updates the form progress for a school
     * 
     * @param schoolId      the UUID of the school
     * @param completedStep the step that was just completed (1-5)
     * @throws IllegalArgumentException if school not found or invalid step
     */
    public void updateFormProgress(UUID schoolId, Integer completedStep) {
        if (completedStep < 1 || completedStep > 5)
            throw new IllegalArgumentException("Form step must be between 1 and 5");

        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new IllegalArgumentException("School not found with id: " + schoolId));

        // Only update if this step is greater than current progress
        if (completedStep > school.getLastFormStep()) {
            school.setLastFormStep(completedStep);

            // Mark forms as completed if all 5 steps are done
            if (completedStep == 5) {
                school.setFormsCompleted(true);
            }

            schoolRepository.save(school);
        }
    }

    /**
     * Marks all forms as completed for a school
     * 
     * @param schoolId the UUID of the school
     */
    public void markFormsCompleted(UUID schoolId) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new IllegalArgumentException("School not found with id: " + schoolId));

        school.setFormsCompleted(true);
        school.setLastFormStep(5);
        schoolRepository.save(school);
    }

    /**
     * Gets the current form progress for a school
     * 
     * @param schoolId the UUID of the school
     * @return FormProgressDto containing current progress
     */
    public FormProgressDto getFormProgress(UUID schoolId) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new IllegalArgumentException("School not found with id: " + schoolId));

        return new FormProgressDto(
                school.getFormsCompleted(),
                school.getLastFormStep(),
                getNextRequiredStep(school.getLastFormStep(), school.getFormsCompleted()));
    }

    private Integer getNextRequiredStep(Integer lastStep, Boolean formsCompleted) {
        if (formsCompleted) {
            return null; // All forms completed
        }
        return Math.min(lastStep + 1, 5);
    }

    // DTO for returning form progress information
    public static class FormProgressDto {
        private final Boolean formsCompleted;
        private final Integer lastFormStep;
        private final Integer nextRequiredStep;

        public FormProgressDto(Boolean formsCompleted, Integer lastFormStep, Integer nextRequiredStep) {
            this.formsCompleted = formsCompleted;
            this.lastFormStep = lastFormStep;
            this.nextRequiredStep = nextRequiredStep;
        }

        // Getters
        public Boolean getFormsCompleted() {
            return formsCompleted;
        }

        public Integer getLastFormStep() {
            return lastFormStep;
        }

        public Integer getNextRequiredStep() {
            return nextRequiredStep;
        }
    }
}