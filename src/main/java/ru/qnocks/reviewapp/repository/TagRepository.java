package ru.qnocks.reviewapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.qnocks.reviewapp.domain.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
