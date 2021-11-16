package ru.qnocks.reviewapp.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.qnocks.reviewapp.domain.Content;
import ru.qnocks.reviewapp.domain.Review;
import ru.qnocks.reviewapp.domain.Role;
import ru.qnocks.reviewapp.domain.User;
import ru.qnocks.reviewapp.repository.ReviewRepository;
import ru.qnocks.reviewapp.repository.RoleRepository;
import ru.qnocks.reviewapp.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
@AllArgsConstructor
public class DbInit implements CommandLineRunner {

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        List<Role> roles = List.of(
                new Role("ROLE_USER"),
                new Role("ROLE_ADMIN")
        );

        List<Review> reviews = List.of(
                new Review(1L, "Есть ли разница между Францем и Apache Kafka", Content.BOOK, "Замок", null,"Текст обзора", 2, 3.6, 2, "./images/1.jpg", null),
                new Review(2L, "name2", Content.MOVIE, "book2", null,"text2", 2, 2.0, 2, "./images/2.jpg", null),
                new Review(3L, "name3", Content.GAME, "book3", null,"text3", 3, 3.0, 3, "./images/3.jpg", null),
                new Review(4L, "name4", Content.BOOK, "book4", null,"text4", 4, 4.0, 4, "./images/4.jpg", null),
                new Review(5L, "name5", Content.MOVIE, "book5", null,"text5", 5, 5.0, 5, "./images/5.jpg", null),
                new Review(6L, "name6", Content.MOVIE, "book6", null,"text6", 6, 6.0, 6, "./images/6.jpg", null),
                new Review(7L, "name6", Content.MOVIE, "book6", null,"text6", 6, 6.0, 6, "./images/6.jpg", null),
                new Review(8L, "name6", Content.MOVIE, "book6", null,"text6", 6, 6.0, 6, "./images/6.jpg", null),
                new Review(9L, "name6", Content.MOVIE, "book6", null,"text6", 6, 6.0, 6, "./images/6.jpg", null),
                new Review(10L, "name6", Content.MOVIE, "book6", null,"text6", 6, 6.0, 6, "./images/6.jpg", null),
                new Review(11L, "name6", Content.MOVIE, "book6", null,"text6", 6, 6.0, 6, "./images/6.jpg", null),
                new Review(12L, "name6", Content.MOVIE, "book6", null,"text6", 6, 6.0, 6, "./images/6.jpg", null),
                new Review(13L, "name6", Content.MOVIE, "book6", null,"text6", 6, 6.0, 6, "./images/6.jpg", null),
                new Review(14L, "name6", Content.MOVIE, "book6", null,"text6", 6, 6.0, 6, "./images/6.jpg", null),
                new Review(15L, "name6", Content.MOVIE, "book6", null,"text6", 6, 6.0, 6, "./images/6.jpg", null),
                new Review(16L, "name6", Content.MOVIE, "book6", null,"text6", 6, 6.0, 6, "./images/6.jpg", null),
                new Review(17L, "name6", Content.MOVIE, "book6", null,"text6", 6, 6.0, 6, "./images/6.jpg", null),
                new Review(18L, "name6", Content.MOVIE, "book6", null,"text6", 6, 6.0, 6, "./images/6.jpg", null)
        );


        List<User> users = List.of(
                new User(0L, "name1", "email1", null,
                        "password", null, false, Set.of(roles.get(0))),
                new User(0L, "name2", "email2", null,
                        "password", null, false, Set.of(roles.get(1))),
                new User(0L, "name3", "email3", null,
                        "password", null, false, Set.of(roles.get(0), roles.get(1)))
        );

        if (reviewRepository.findAll().isEmpty() && userRepository.findAll().isEmpty() && roleRepository.findAll().isEmpty()) {
            log.info("ADDED TEST DATA");
            reviewRepository.saveAll(reviews);
            roleRepository.saveAll(roles);
            userRepository.saveAll(users);
        }
    }
}
