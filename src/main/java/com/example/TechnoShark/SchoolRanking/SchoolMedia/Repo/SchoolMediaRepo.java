package com.example.TechnoShark.SchoolRanking.SchoolMedia.Repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TechnoShark.SchoolRanking.SchoolMedia.Model.SchoolMedia;

@Repository
public interface SchoolMediaRepo extends JpaRepository<SchoolMedia, UUID> {

}
