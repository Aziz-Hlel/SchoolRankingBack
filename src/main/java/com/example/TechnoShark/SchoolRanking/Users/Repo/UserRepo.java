package com.example.TechnoShark.SchoolRanking.Users.Repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TechnoShark.SchoolRanking.Users.Model.User;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    // @Query("SELECT u FROM User u LEFT JOIN FETCH u.school WHERE u.email = :email")
    // Optional<User> findByEmailWithSchool(String email);

}
