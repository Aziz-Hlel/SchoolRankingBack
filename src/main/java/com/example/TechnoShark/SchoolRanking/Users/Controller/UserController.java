package com.example.TechnoShark.SchoolRanking.Users.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TechnoShark.SchoolRanking.Auth.DTO.JwtUserResponse;
import com.example.TechnoShark.SchoolRanking.Auth.Util.UserContext;
import com.example.TechnoShark.SchoolRanking.Users.DTO.CreateUserRequest;
import com.example.TechnoShark.SchoolRanking.Users.DTO.UpdateUserRequest;
import com.example.TechnoShark.SchoolRanking.Users.DTO.UserPageResponse;
import com.example.TechnoShark.SchoolRanking.Users.DTO.UserResponse;
import com.example.TechnoShark.SchoolRanking.Users.DTO.getUsersPageRequest;
import com.example.TechnoShark.SchoolRanking.Users.Service.UserService;
import com.example.TechnoShark.SchoolRanking.Utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping({ "/users", "/user" })
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    // @PreAuthorize("hasRole('ADMIN') or @postService.isOwner(#id,
    // authentication.name)") // Complex authorization

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping({ "", "/" })
    ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest userRequest) {
        // if ("admin".equals(userRequest.getSchool()))
        // throw new ResponseStatusException(HttpStatus.CONFLICT, "User already
        // exists");
        UserResponse response = userService.createUser(userRequest);

        ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>builder()
                .message("User created successfully")
                .success(true)
                .data(response)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{userId}")
    ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable UUID userId,
            @Valid @RequestBody UpdateUserRequest userRequest) {

        UserResponse response = userService.updateUser(userId, userRequest);

        // throw new ResourceNotFoundException(null);
        ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>builder()
                .message("User updated successfully")
                .success(true)
                .data(response)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    UserResponse getUser(@PathVariable UUID id) {
        UserResponse userOpt = userService.getUser(id);
        return userOpt;
    }

    @GetMapping({ "", "/" })
    public ResponseEntity<ApiResponse<Page<UserPageResponse>>> getPageUsers(
            @Valid @ModelAttribute getUsersPageRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPage() - 1,
                request.getSize(),
                Sort.by(parseSort(request.getSort())));

        Page<UserPageResponse> users = userService.getAllUsers(pageable);

        ApiResponse<Page<UserPageResponse>> apiResponse = ApiResponse.<Page<UserPageResponse>>builder()
                .message("Users retrieved successfully")
                .success(true)
                .data(users)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    public static List<Sort.Order> parseSort(List<String> sortParams) {
        return sortParams.stream()
                .map(param -> {
                    String[] parts = param.split(",");
                    String property = parts[0].trim();
                    Sort.Direction direction = parts.length > 1
                            ? Sort.Direction.fromString(parts[1].trim())
                            : Sort.Direction.ASC;
                    return new Sort.Order(direction, property);
                })
                .toList();
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
