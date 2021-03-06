package ru.qnocks.reviewapp.service;

import lombok.RequiredArgsConstructor;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.qnocks.reviewapp.domain.Review;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {

    @PersistenceContext
    private final EntityManager entityManager;

    public List<Review> findReviews(String text) {
        FullTextEntityManager entityManager = Search.getFullTextEntityManager(this.entityManager);

        QueryBuilder queryBuilder = entityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Review.class)
                .get();

        Query query = queryBuilder
                .keyword()
                .onFields("name", "text", "content.name", "contentName", "tags.name")
                .matching(text)
                .createQuery();

        FullTextQuery jpaQuery = entityManager.createFullTextQuery(query, Review.class);

//        jpaQuery.setMaxResults(pageable.getPageSize());

        List<Review> reviews = jpaQuery.getResultList();

        return reviews;
//        return new PageImpl<>(reviews, pageable, jpaQuery.getResultSize());
    }
}
