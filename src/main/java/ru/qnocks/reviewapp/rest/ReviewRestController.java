package ru.qnocks.reviewapp.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.qnocks.reviewapp.dto.ReviewDto;
import ru.qnocks.reviewapp.service.ReviewService;

@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin(origins = {"http://localhost:3000", "https://review-frontend-app.herokuapp.com"})
@RequiredArgsConstructor
public class ReviewRestController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<Page<ReviewDto>> list(@PageableDefault(sort = "id") Pageable pageable,
                                                @RequestParam(value = "search", required = false) String search) {
        return new ResponseEntity<>(reviewService.getAll(pageable, search), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ReviewDto> show(@PathVariable("id") Long id) {
        return new ResponseEntity<>(reviewService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ReviewDto> create(@RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.create(reviewDto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ReviewDto> update(@PathVariable("id") Long id, @RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.update(id, reviewDto), HttpStatus.OK);
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
        return new ResponseEntity<>(reviewService.upload(id, file), HttpStatus.OK);
    }
}


