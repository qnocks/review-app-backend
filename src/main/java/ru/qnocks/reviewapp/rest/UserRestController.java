package ru.qnocks.reviewapp.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.qnocks.reviewapp.domain.User;
import ru.qnocks.reviewapp.dto.UserDto;
import ru.qnocks.reviewapp.service.DtoMapperService;
import ru.qnocks.reviewapp.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = {"http://localhost:3000", "https://review-frontend-app.herokuapp.com"})
@AllArgsConstructor
public class UserRestController {

    private final UserService userService;

    private final DtoMapperService mapperService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> list() {
        List<UserDto> users = userService.getAll().stream()
                .map(mapperService::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> show(@PathVariable("id") Long id) {
        UserDto user = mapperService.toDto(userService.getById(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        User user = mapperService.toEntity(userDto);
        User savedUser = userService.create(user);
        return new ResponseEntity<>(mapperService.toDto(savedUser), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserDto> update(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        User user = mapperService.toEntity(userDto);
        User updatedUser = userService.update(id, user);
        return new ResponseEntity<>(mapperService.toDto(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

