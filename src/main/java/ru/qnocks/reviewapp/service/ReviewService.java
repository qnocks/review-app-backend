package ru.qnocks.reviewapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.qnocks.reviewapp.domain.Review;
import ru.qnocks.reviewapp.repository.ReviewRepository;

import java.util.List;


@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public Review getById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> {
                    String e = "Cannot find Review with id " + id;
                    log.error(e);
                    throw new IllegalArgumentException(e);
                });
        System.out.println("getById: + " + id + " : " + review);
        return review;
    }

    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    public Review update(Long id, Review review) {
        Review existingReview = getById(id);
        BeanUtils.copyProperties(review, existingReview, "id");
        reviewRepository.save(existingReview);
        return existingReview;
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}
