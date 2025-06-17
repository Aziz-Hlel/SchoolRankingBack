package com.example.TechnoShark.SchoolRanking.Users.Service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.TechnoShark.SchoolRanking.Enums.RoleEnums;
import com.example.TechnoShark.SchoolRanking.ErrorHandler.Exceptions.ResourceNotFoundException;
import com.example.TechnoShark.SchoolRanking.Schools.Model.School;
import com.example.TechnoShark.SchoolRanking.Users.DTO.CreateUserRequest;
import com.example.TechnoShark.SchoolRanking.Users.DTO.CreateUserResponse;
import com.example.TechnoShark.SchoolRanking.Users.DTO.UserResponse;
import com.example.TechnoShark.SchoolRanking.Users.Model.User;
import com.example.TechnoShark.SchoolRanking.Users.Repo.UserRepo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public CreateUserResponse createUser(CreateUserRequest userRequest) {

        Optional<User> existingUser = userRepo.findByEmail(userRequest.getEmail());

        if (existingUser.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");

        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .school(null)
                .role(RoleEnums.SUPER_ADMIN)
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();

        userRepo.save(newUser);

        return new CreateUserResponse(
                newUser.getId(),
                newUser.getFirstName(),
                newUser.getLastName(),
                newUser.getEmail(),
                null,
                newUser.getRole());

    }

    public UserResponse getUser(UUID id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        School school = user.getSchool();

        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                school != null ? school.getId() : null,
                user.getRole(), user.getEmail());

    }
}
