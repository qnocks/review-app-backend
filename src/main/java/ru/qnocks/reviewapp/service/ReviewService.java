package ru.qnocks.reviewapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.qnocks.reviewapp.domain.Review;
import ru.qnocks.reviewapp.domain.Role;
import ru.qnocks.reviewapp.domain.User;
import ru.qnocks.reviewapp.repository.ReviewRepository;
import ru.qnocks.reviewapp.repository.RoleRepository;
import ru.qnocks.reviewapp.security.UserDetailsImpl;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserService userService;

    private final RoleRepository roleRepository;

    public List<Review> getAll() {
        List<Review> all = reviewRepository.findAll();
        return all;
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

    public Review create(Review review, UserDetailsImpl userDetails) {
        Long id = userDetails.getId();
        String username = userDetails.getUsername();
        String email = userDetails.getEmail();
        String password = userDetails.getPassword();

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        Set<Role> roles = authorities.stream()
                .map(role -> roleRepository.findByName(role.getAuthority()).orElseThrow(IllegalArgumentException::new))
                .collect(Collectors.toSet());

        Set<Review> reviews = userDetails.getReviews();
        reviews.add(review);

//        Review createdReview = reviewRepository.save(review);

        userService.update(id, new User(id, username, email, null, password, null, true, roles, reviews));

        return review;
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
