package com.example.TechnoShark.SchoolRanking.UserSchool.Repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.TechnoShark.SchoolRanking.Schools.Model.School;
import com.example.TechnoShark.SchoolRanking.UserSchool.Model.UserSchool;

@Repository
public interface UserSchoolRepo extends JpaRepository<UserSchool, UUID> {

    @Query("SELECT us FROM UserSchool us WHERE us.user.id = :userId ")
    List<UserSchool> findByUserId(@Param("userId") UUID userId);

    @Query("SELECT us.school FROM UserSchool us WHERE us.user.id = :userId ")
    List<School> findSchoolsByUserId(@Param("userId") UUID userId);

    @Query("SELECT COUNT(us) > 0 FROM UserSchool us WHERE us.user.id = :userId AND us.school.id = :schoolId ")
    boolean existsByUserIdAndSchoolId(@Param("userId") UUID userId, @Param("schoolId") UUID schoolId);

    @Query("SELECT us FROM UserSchool us WHERE us.user.id = :userId AND us.school.id = :schoolId ")
    Optional<UserSchool> findByUserIdAndSchoolId(@Param("userId") UUID userId,
            @Param("schoolId") UUID schoolId);

}
