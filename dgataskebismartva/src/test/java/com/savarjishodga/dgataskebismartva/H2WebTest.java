package com.savarjishodga.dgataskebismartva;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
public class H2WebTest {

    public static void main(String[] args) {
        SpringApplication app =
                new SpringApplication(DgataskebismartvaApplication.class);

        app.setAdditionalProfiles("test");

        app.run(args);
    }
}