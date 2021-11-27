package ru.qnocks.reviewapp.rest;

import lombok.RequiredArgsConstructor;
import org.hibernate.search.jpa.Search;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.qnocks.reviewapp.domain.Review;
import ru.qnocks.reviewapp.service.SearchService;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TestRestController {

    private final SearchService searchService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/search")
    public List<Review> getReviews(@RequestParam("search") String search) {
        return searchService.findReviews(search);
    }
}
