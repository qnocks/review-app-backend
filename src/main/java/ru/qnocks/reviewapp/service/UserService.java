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
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final DtoMapperService mapperService;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(mapperService::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    String e = "Cannot find User with id " + id;
                    log.error(e);
                    throw new IllegalArgumentException(e);
                });

        return mapperService.toDto(user);
    }

    public UserDto create(UserDto userDto) {
        User user = mapperService.toEntity(userDto);
        return mapperService.toDto(userRepository.save(user));
    }

    public UserDto update(Long id, UserDto userDto) {
        User user = mapperService.toEntity(userDto);
        User existingUser = userRepository.getById(id);

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

        return mapperService.toDto(userRepository.save(existingUser));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public Boolean existsByUsername(String name) {
        return userRepository.existsByUsername(name);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean validate(UserDto userDto, User user) {
        return passwordEncoder.matches(userDto.getPassword(), user.getPassword());
    }
}
