package ru.qnocks.reviewapp.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.qnocks.reviewapp.domain.Review;
import ru.qnocks.reviewapp.dto.ReviewDto;
import ru.qnocks.reviewapp.dto.UserDto;
import ru.qnocks.reviewapp.service.DtoMapperService;
import ru.qnocks.reviewapp.service.ReviewService;
import ru.qnocks.reviewapp.service.SearchService;
import ru.qnocks.reviewapp.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://review-frontend-app.herokuapp.com"})
public class TestRestController {

    private final SearchService searchService;

    private final ReviewService reviewService;

    private final UserService userService;

//    private final TagRepository tagRepository;

    private final DtoMapperService mapperService;

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
        List<Review> reviews = searchService.findReviews(search);
        return reviews;
    }

    @GetMapping("/reviews")
    public List<ReviewDto> getAllReviews() {
        return reviewService.getAll().stream()
                .map(mapperService::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.getAll().stream()
                .map(mapperService::toDto)
                .collect(Collectors.toList());
    }

//    @GetMapping("/tags")
//    public List<TagDto> getAllTags() {
//        return tagRepository.findAll().stream()
//                .map(mapperService::toDto)
//                .collect(Collectors.toList());
//    }
}
