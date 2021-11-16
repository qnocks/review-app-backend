package ru.qnocks.reviewapp.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.qnocks.reviewapp.domain.Review;
import ru.qnocks.reviewapp.service.ReviewService;

import java.util.List;

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
    public ResponseEntity<Review> show(@PathVariable("id") Long id) {
        return new ResponseEntity<>(reviewService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Review> create(@RequestBody Review review) {
        return new ResponseEntity<>(reviewService.create(review), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Review> update(@PathVariable("id") Long id, @RequestBody Review review) {
        return new ResponseEntity<>(reviewService.update(id, review), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reviewService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


