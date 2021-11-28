package ru.qnocks.reviewapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.qnocks.reviewapp.domain.Review;
import ru.qnocks.reviewapp.domain.User;
import ru.qnocks.reviewapp.dto.ReviewDto;
import ru.qnocks.reviewapp.dto.UserDto;
import ru.qnocks.reviewapp.repository.UserRepository;

@Service
public class DtoMapperService {

    private final ModelMapper mapper;

    private final UserRepository userRepository;

    @Autowired
    public DtoMapperService(ModelMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        mapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public ReviewDto toDto(Review review) {
        mapper.typeMap(Review.class, ReviewDto.class).addMappings(mapper -> {
            mapper.map(Review::getContentName, ReviewDto::setContentName);
        });
        return mapper.map(review, ReviewDto.class);
    }

    public Review toEntity(ReviewDto reviewDto) {
        mapper.typeMap(ReviewDto.class, Review.class).addMappings(mapper -> {
            mapper.map(ReviewDto::getContentName, Review::setContentName);
        });

        User user = userRepository.findById(reviewDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Review with id " + reviewDto.getUserId()));

        Review review = mapper.map(reviewDto, Review.class);
        review.setUser(user);
        return review;
    }

    public UserDto toDto(User user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        return userDto;
    }

    public User toEntity(UserDto userDto) {
        return mapper.map(userDto, User.class);
    }
}
