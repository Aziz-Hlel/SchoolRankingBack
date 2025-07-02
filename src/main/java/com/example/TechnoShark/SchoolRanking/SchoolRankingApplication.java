package com.example.TechnoShark.SchoolRanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class SchoolRankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolRankingApplication.class, args);
		// * you can use EntityManager.getReference(...) istead of fetching and
		// refrenching the whole Entity to add it as a foreign object to a new Entity
	}

	@GetMapping
	public String getMethodName() {
		return "scwxxxx";
	};

}
