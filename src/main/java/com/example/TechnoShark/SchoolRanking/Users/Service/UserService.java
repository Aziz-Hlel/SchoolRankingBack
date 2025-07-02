package com.example.TechnoShark.SchoolRanking.Users.Service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.TechnoShark.SchoolRanking.Enums.RoleEnums;
import com.example.TechnoShark.SchoolRanking.ErrorHandler.Exceptions.ResourceNotFoundException;
import com.example.TechnoShark.SchoolRanking.Users.DTO.CreateUserRequest;
import com.example.TechnoShark.SchoolRanking.Users.DTO.UpdateUserRequest;
import com.example.TechnoShark.SchoolRanking.Users.DTO.UserPageResponse;
import com.example.TechnoShark.SchoolRanking.Users.DTO.UserResponse;
import com.example.TechnoShark.SchoolRanking.Users.Mapper.UserMapper;
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

    private final UserMapper userMapper;

    @Transactional
    public UserResponse createUser(CreateUserRequest userRequest) {

        Optional<User> existingUser = userRepo.findByEmail(userRequest.getEmail());

        if (existingUser.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");

        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .role(RoleEnums.ADMIN)
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();

        userRepo.save(newUser);

        return new UserResponse(
                newUser.getId(),
                newUser.getFirstName(),
                newUser.getLastName(),
                newUser.getRole(),
                newUser.getEmail());

    }

    @Transactional
    public UserResponse updateUser(UUID id, UpdateUserRequest userRequest) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        User updatedEntity = userMapper.toUpdatedEntity(userRequest, user);

        User updatedUser = userRepo.save(updatedEntity);

        UserResponse dto = userMapper.toDto(updatedUser);

        return dto;
    }

    @Transactional(readOnly = true) // Skips dirty checks and optimizes session and avoid lazy loading issues if
                                    // your DTO mapper accesses related entities
    public UserResponse getUser(UUID id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));


        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                // user.getSchools(),
                user.getRole(), user.getEmail());

    }

    @Transactional(readOnly = true)
    public Page<UserPageResponse> getAllUsers(Pageable pageable) {

        Page<User> users = userRepo.findAll(pageable);
        Page<UserPageResponse> page = users.map(userMapper::toPageDto);

        return page;
    }

}
