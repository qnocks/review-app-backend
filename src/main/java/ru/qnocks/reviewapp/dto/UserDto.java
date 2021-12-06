package ru.qnocks.reviewapp.dto;

import lombok.*;
import ru.qnocks.reviewapp.domain.Role;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = "reviews")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto {

    private Long id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String password;

    private String newPassword;

    private Boolean active;

    private Set<Role> roles = new HashSet<>();

    private Set<ReviewDto> reviews = new HashSet<>();
}
