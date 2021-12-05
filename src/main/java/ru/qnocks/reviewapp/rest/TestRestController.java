package ru.qnocks.reviewapp.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.qnocks.reviewapp.domain.Review;
import ru.qnocks.reviewapp.dto.ReviewDto;
import ru.qnocks.reviewapp.dto.UserDto;
import ru.qnocks.reviewapp.service.*;

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

    private final CloudinaryService cloudinaryService;


    private final DtoMapperService mapperService;

    @GetMapping("/search")
    public List<Review> getReviews(@RequestParam("search") String search) {
//        List<Review> reviews = searchService.findReviews(search);
//        return reviews;
        return null;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        String upload = cloudinaryService.upload(file);
        return new ResponseEntity<>(upload, HttpStatus.OK);
    }
}
