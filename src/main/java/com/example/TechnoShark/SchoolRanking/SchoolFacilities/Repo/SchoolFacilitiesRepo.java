package com.example.TechnoShark.SchoolRanking.SchoolFacilities.Repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TechnoShark.SchoolRanking.SchoolFacilities.Model.SchoolFacilities;

@Repository
public interface SchoolFacilitiesRepo extends JpaRepository<SchoolFacilities, UUID> {

}
