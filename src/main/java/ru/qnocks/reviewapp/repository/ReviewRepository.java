package ru.qnocks.reviewapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.qnocks.reviewapp.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
