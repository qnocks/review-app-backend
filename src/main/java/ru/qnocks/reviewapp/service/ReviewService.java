package ru.qnocks.reviewapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.qnocks.reviewapp.domain.Review;
import ru.qnocks.reviewapp.domain.Role;
import ru.qnocks.reviewapp.repository.ReviewRepository;
import ru.qnocks.reviewapp.repository.RoleRepository;
import ru.qnocks.reviewapp.repository.TagRepository;
import ru.qnocks.reviewapp.security.UserDetailsImpl;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserService userService;

    private final SearchService searchService;

    private final CloudinaryService cloudinaryService;

    private final RoleRepository roleRepository;

    private final TagRepository tagRepository;

    public Page<Review> getAll(Pageable pageable, String search) {
        if (search != null) {
            return searchService.findReviews(search, pageable);
        }        
        return reviewRepository.findAll(pageable);
    }

    @Transactional
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

    @Transactional
    public Review create(Review review, UserDetailsImpl userDetails) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        Set<Role> roles = authorities.stream()
                .map(role -> roleRepository.findByName(role.getAuthority()).orElseThrow(IllegalArgumentException::new))
                .collect(Collectors.toSet());

        Set<Review> reviews = review.getUser().getReviews();
        reviews.add(review);

//        userService.update(id, new User(id, username, email, password, true, roles, reviews));

        tagRepository.saveAll(review.getTags());
        return reviewRepository.save(review);
    }

    public Review update(Long id, Review review) {
        Review existingReview = getById(id);
        BeanUtils.copyProperties(review, existingReview, "id");

        tagRepository.saveAll(review.getTags());

        Review savedReview = reviewRepository.save(existingReview);

        return savedReview;
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    public Review upload(Long id, MultipartFile file) {
        String imageLink = cloudinaryService.upload(file);

        Review review = getById(id);
        review.setImagesLink(imageLink);
        return update(id, review);
    }
}
