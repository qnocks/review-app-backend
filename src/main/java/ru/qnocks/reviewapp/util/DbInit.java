package ru.qnocks.reviewapp.util;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.qnocks.reviewapp.domain.*;
import ru.qnocks.reviewapp.repository.ReviewRepository;
import ru.qnocks.reviewapp.repository.RoleRepository;
import ru.qnocks.reviewapp.repository.TagRepository;
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

    private final TagRepository tagRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
//        Faker faker = new Faker();
//        String text = faker.yoda().quote();
//
//        List<Role> roles = List.of(
//                new Role("ROLE_USER"),
//                new Role("ROLE_ADMIN")
//        );
//
//        Review review1 = new Review();
//        User user1 = new User();
//
//            review1.setName("Есть ли разница между Францем и Apache Kafka");
//            review1.setContent(Content.BOOK);
//            review1.setContentName("Замок");
//            review1.setText(text);
////            review1.setUser(user1);
//
//            user1.setUsername("name1");
//            user1.setEmail("email1@mail.ru");
//            user1.setPassword(passwordEncoder.encode("password"));
//            user1.setRoles(Set.of(roles.get(0)));
//            user1.setReviews(Set.of(review1));
//
////        reviewRepository.save(review1);
//        userRepository.save(user1);
//
//        Review review2 = new Review();
//        User user2 = new User();
//
//            review2.setName("Есть ли разница между 2 и 2");
//            review2.setContent(Content.BOOK);
//            review2.setContentName("Название2");
//            review2.setText(text);
//            review2.setUser(user2);
//
//            user2.setUsername("name2");
//            user2.setEmail("email2@mail.ru");
//            user2.setPassword(passwordEncoder.encode("password"));
//            user2.setRoles(Set.of(roles.get(0)));
//            user2.setReviews(Set.of(review2));
//
//
//        Review review3 = new Review();
//        User user3 = new User();
//
//            review3.setName("Есть ли разница между 3 и 3");
//            review3.setContent(Content.GAME);
//            review3.setContentName("Название3");
//            review3.setText(text);
//            review3.setUser(user3);
//
//            user3.setUsername("name3");
//            user3.setEmail("email3@mail.ru");
//            user3.setPassword(passwordEncoder.encode("password"));
//            user3.setRoles(Set.of(roles.get(1)));
//            user3.setReviews(Set.of(review3));
//
//        List<User> users = List.of(
//                user1, user2, user3
//        );
//
//        List<Review> reviews = List.of(
//                review1, review2, review3
//        );
//
//        reviewRepository.deleteAll();
//        userRepository.deleteAll();
//        roleRepository.deleteAll();
//        log.info("DELETED TEST DATA");
//
////        userRepository.saveAll(users);
////        reviewRepository.saveAll(reviews);
//        roleRepository.saveAll(roles);
//        log.info("ADDED TEST DATA");
//
////        for (int i = 0; i < 100; i++) {
////            Review review = new Review();
////            review.setName(faker.harryPotter().quote());
////            review.setText(faker.yoda().quote());
////            reviewRepository.save(review);
////        }
    }
}
