package ru.qnocks.reviewapp.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.qnocks.reviewapp.domain.Review;
import ru.qnocks.reviewapp.security.UserDetailsImpl;
import ru.qnocks.reviewapp.service.ReviewService;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewRestController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> list() {
        return new ResponseEntity<>(reviewService.getAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Review> show(@PathVariable("id") Long id) {
        return new ResponseEntity<>(reviewService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Review> create(@RequestBody Review review,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(reviewService.create(review, userDetails), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Review> update(@PathVariable("id") Long id, @RequestBody Review review) {
        return new ResponseEntity<>(reviewService.update(id, review), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reviewService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


