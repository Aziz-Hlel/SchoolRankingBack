package com.example.TechnoShark.SchoolRanking.Seeders;

import java.util.List;

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

    public void seed() {
        if (userRepo.count() != 0)
            return;

        User superAdmin = User.builder()
                .firstName("Super")
                .lastName("Admin")
                .email("superadmin@example.com")
                .password(passwordEncoder.encode("superadmin"))
                .role(RoleEnums.SUPER_ADMIN)
                .build();


        userRepo.saveAll(List.of(superAdmin));
    }

}
