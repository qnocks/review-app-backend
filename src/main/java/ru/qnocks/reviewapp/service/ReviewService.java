package ru.qnocks.reviewapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.qnocks.reviewapp.domain.Review;
import ru.qnocks.reviewapp.dto.ReviewDto;
import ru.qnocks.reviewapp.repository.ReviewRepository;

import java.util.Set;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final DtoMapperService mapperService;

    private final SearchService searchService;

    private final CloudinaryService cloudinaryService;

    private final TagService tagService;

    @Transactional(readOnly = true)
    public Page<ReviewDto> getAll(Pageable pageable, String search) {
        if (search != null) {
            return searchService.findReviews(search, pageable).map(mapperService::toDto);
        }
        return reviewRepository.findAll(pageable).map(mapperService::toDto);
    }

    @Transactional(readOnly = true)
    public ReviewDto getById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> {
                    String e = "Cannot find Review with id " + id;
                    log.error(e);
                    throw new IllegalArgumentException(e);
                });

        return mapperService.toDto(review);
    }

    public ReviewDto create(ReviewDto reviewDto) {
        Review review = mapperService.toEntity(reviewDto);

        Set<Review> reviews = review.getUser().getReviews();
        reviews.add(review);

        tagService.saveAll(review.getTags());

        return mapperService.toDto(reviewRepository.save(review));
    }

    public ReviewDto update(Long id, ReviewDto reviewDto) {
        Review review = mapperService.toEntity(reviewDto);
        Review existingReview = reviewRepository.getById(id);

        BeanUtils.copyProperties(review, existingReview, "id");

        tagService.saveAll(review.getTags());

        return mapperService.toDto(reviewRepository.save(existingReview));
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    public ReviewDto upload(Long id, MultipartFile file) {
        String imageLink = cloudinaryService.upload(file);
        Review review = reviewRepository.getById(id);
        review.setImagesLink(imageLink);

        return update(id, mapperService.toDto(review));
    }
}
