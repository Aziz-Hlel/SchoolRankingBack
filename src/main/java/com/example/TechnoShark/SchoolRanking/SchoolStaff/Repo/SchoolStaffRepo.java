package com.example.TechnoShark.SchoolRanking.SchoolStaff.Repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TechnoShark.SchoolRanking.SchoolStaff.Model.SchoolStaff;

public interface SchoolStaffRepo extends JpaRepository<SchoolStaff, UUID> {

}
