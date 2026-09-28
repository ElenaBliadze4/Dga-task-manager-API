package com.savarjishodga.dgataskebismartva;


import org.springframework.boot.SpringApplication;

public class H2WebTest {

    public static void main(String[] args) {
        SpringApplication app =
                new SpringApplication(DgataskebismartvaApplication.class);

        app.setAdditionalProfiles("test");

        app.run(args);
    }
}