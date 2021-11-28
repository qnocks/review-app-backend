package ru.qnocks.reviewapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.qnocks.reviewapp.domain.User;
import ru.qnocks.reviewapp.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        List<User> all = userRepository.findAll();
        return all;
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    String e = "Cannot find User with id " + id;
                    log.error(e);
                    throw new IllegalArgumentException(e);
                });
    }

    public User create(User review) {
        return userRepository.save(review);
    }

    @Transactional
    public User update(Long id, User user) {
        User existingUser = getById(id);

        if (user.getRoles().isEmpty()) {
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        else {
            BeanUtils.copyProperties(user, existingUser, "id", "password");
        }

        userRepository.save(existingUser);
        return existingUser;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}
