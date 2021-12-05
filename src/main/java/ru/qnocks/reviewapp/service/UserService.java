package ru.qnocks.reviewapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.qnocks.reviewapp.domain.User;
import ru.qnocks.reviewapp.dto.UserDto;
import ru.qnocks.reviewapp.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final DtoMapperService mapperService;

    private final PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    String e = "Cannot find User with id " + id;
                    log.error(e);
                    throw new IllegalArgumentException(e);
                });
    }

    @Transactional
    public User create(User review) {
        return userRepository.save(review);
    }

    @Transactional
    public User update(Long id, UserDto userDto) {
        User user = mapperService.toEntity(userDto);
        User existingUser = getById(id);

        if (userDto.getNewPassword() != null && validate(userDto, existingUser)) {
            user.setPassword(userDto.getNewPassword());
        }

        if (user.getRoles().isEmpty()) {
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        else {
            BeanUtils.copyProperties(user, existingUser, "id", "password");
        }

        return userRepository.save(existingUser);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private boolean validate(UserDto userDto, User user) {
        return passwordEncoder.matches(userDto.getPassword(), user.getPassword());
    }


}
