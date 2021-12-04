package ru.qnocks.reviewapp.rest;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.qnocks.reviewapp.domain.Review;
import ru.qnocks.reviewapp.dto.ReviewDto;
import ru.qnocks.reviewapp.dto.TagDto;
import ru.qnocks.reviewapp.security.UserDetailsImpl;
import ru.qnocks.reviewapp.service.CloudinaryService;
import ru.qnocks.reviewapp.service.DtoMapperService;
import ru.qnocks.reviewapp.service.ReviewService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin(origins = {"http://localhost:3000", "https://review-frontend-app.herokuapp.com"})
public class ReviewRestController {

    private final ReviewService reviewService;

    private final DtoMapperService mapperService;

    @GetMapping
    public ResponseEntity<List<ReviewDto>> list() {
        List<ReviewDto> reviews = reviewService.getAll().stream()
                .map(mapperService::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ReviewDto> show(@PathVariable("id") Long id) {
        ReviewDto review = mapperService.toDto(reviewService.getById(id));
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ReviewDto> create(@RequestBody ReviewDto reviewDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Review review = mapperService.toEntity(reviewDto);
        Review savedReviews = reviewService.create(review, userDetails);
        return new ResponseEntity<>(mapperService.toDto(savedReviews), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ReviewDto> update(@PathVariable("id") Long id, @RequestBody ReviewDto reviewDto) {
        Review review = mapperService.toEntity(reviewDto);
        Review updatedReview = reviewService.update(id, review);
        return new ResponseEntity<>(mapperService.toDto(updatedReview), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reviewService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("{id}/upload")
    public ResponseEntity<ReviewDto> uploadImage(@PathVariable("id") Long id,
                                                 @RequestParam("image") MultipartFile file) {
        // TODO: перенести в update
        Review review = reviewService.upload(id, file);
        return new ResponseEntity<>(mapperService.toDto(review), HttpStatus.OK);
    }
}


