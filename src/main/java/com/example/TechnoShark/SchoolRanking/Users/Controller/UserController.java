package com.example.TechnoShark.SchoolRanking.Users.Controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TechnoShark.SchoolRanking.Auth.Util.UserContext;
import com.example.TechnoShark.SchoolRanking.Users.DTO.CreateUserRequest;
import com.example.TechnoShark.SchoolRanking.Users.DTO.CreateUserResponse;
import com.example.TechnoShark.SchoolRanking.Users.DTO.JwtUserResponse;
import com.example.TechnoShark.SchoolRanking.Users.DTO.UserResponse;
import com.example.TechnoShark.SchoolRanking.Users.Service.UserService;
import com.example.TechnoShark.SchoolRanking.Utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping({ "/users", "/user" })
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping({ "", "/" })
    ResponseEntity<ApiResponse<CreateUserResponse>> createUser(@Valid @RequestBody CreateUserRequest userRequest) {
        // if ("admin".equals(userRequest.getSchool()))
        // throw new ResponseStatusException(HttpStatus.CONFLICT, "User already
        // exists");
        CreateUserResponse response = userService.createUser(userRequest);

        ApiResponse<CreateUserResponse> apiResponse = ApiResponse.<CreateUserResponse>builder()
                .message("User created successfully")
                .success(true)
                .data(response)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/{id}")
    UserResponse getUser(@PathVariable UUID id) {
        UserResponse userOpt = userService.getUser(id);
        return userOpt;
    }

    // require authentcation on a method
    // if a controller permitted from authentication you can add this annotation to
    @PreAuthorize("isAuthenticated()")
    @GetMapping({ "/me" })
    public ResponseEntity<ApiResponse<JwtUserResponse>> getCurrentUser() {

        JwtUserResponse user = UserContext.getCurrentUser();

        ApiResponse<JwtUserResponse> apiResponse = ApiResponse.<JwtUserResponse>builder()
                .message("User retrieved successfully")
                .success(true)
                .data(user)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(apiResponse);

    }

}
