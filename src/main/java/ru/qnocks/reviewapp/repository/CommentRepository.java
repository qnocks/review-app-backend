package ru.qnocks.reviewapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.qnocks.reviewapp.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
