package ru.qnocks.reviewapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReviewAppApplication {

    // auth branch

    // TODO:
    // 1. Check best practice for @PreAuthorize

    public static void main(String[] args) {
        SpringApplication.run(ReviewAppApplication.class, args);
    }

}
