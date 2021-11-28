package ru.qnocks.reviewapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.qnocks.reviewapp.domain.Role;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private Boolean active;

    private Set<Role> roles = new HashSet<>();

    private Set<ReviewDto> reviews = new HashSet<>();
}
