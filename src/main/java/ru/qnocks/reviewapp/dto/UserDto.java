package ru.qnocks.reviewapp.dto;

import lombok.*;
import ru.qnocks.reviewapp.domain.Role;

import java.util.HashSet;
import java.util.Set;

//@Data
@Getter
@Setter
@ToString(exclude = "reviews")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private String password;

    private Boolean active;

    private Set<Role> roles = new HashSet<>();

    private Set<ReviewDto> reviews = new HashSet<>();
}
