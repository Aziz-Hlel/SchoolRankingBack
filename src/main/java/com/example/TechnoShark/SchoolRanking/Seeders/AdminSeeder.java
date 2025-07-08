package com.example.TechnoShark.SchoolRanking.Seeders;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.TechnoShark.SchoolRanking.Enums.RoleEnums;
import com.example.TechnoShark.SchoolRanking.Users.Model.User;
import com.example.TechnoShark.SchoolRanking.Users.Repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminSeeder {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public void createSuperAdmin(String firstName, String lastName, String email, String password) {
        User superAdmin = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(RoleEnums.SUPER_ADMIN)
                .build();

        userRepo.save(superAdmin);
    }

    public void seed() {

        if (userRepo.count() != 0)
            return;

        createSuperAdmin("Super", "Admin", "superadmin@example.com", "superadmin");

    }

}
