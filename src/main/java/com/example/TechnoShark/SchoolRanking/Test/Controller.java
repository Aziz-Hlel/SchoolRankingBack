package com.example.TechnoShark.SchoolRanking.Test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/test")
public class Controller {

    @GetMapping()
    public String getMethodName() {
        return "xxxx";
    }


    @PostMapping()
    public String postMethodName(@Valid @RequestBody UserRequest userRequest) {
        return "yyyy";
    }



}
