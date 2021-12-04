package ru.qnocks.reviewapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReviewAppApplication {

    // TODO:
    // 1. Check best practice for @PreAuthorize
    // 2. Split up login in AuthRestController into Service layer
    // 3. Add exception handler for API
    // 4. Util package for mapper and search?
    // 5. Cloudinary
    // 6. LiquidBase

    public static void main(String[] args) {
        SpringApplication.run(ReviewAppApplication.class, args);
    }

}
