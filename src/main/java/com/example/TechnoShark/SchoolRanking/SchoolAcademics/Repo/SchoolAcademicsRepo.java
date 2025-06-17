package com.example.TechnoShark.SchoolRanking.SchoolAcademics.Repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TechnoShark.SchoolRanking.SchoolAcademics.Model.SchoolAcademics;

@Repository
public interface SchoolAcademicsRepo extends JpaRepository<SchoolAcademics, UUID> {

}
