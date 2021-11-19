package ru.qnocks.reviewapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.qnocks.reviewapp.domain.User;
import ru.qnocks.reviewapp.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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

    public User update(Long id, User user) {
        User existingUser = getById(id);
        BeanUtils.copyProperties(user, existingUser, "id", "password");
        userRepository.save(existingUser);
        return existingUser;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}
