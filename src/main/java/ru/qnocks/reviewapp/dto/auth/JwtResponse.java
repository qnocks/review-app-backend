package ru.qnocks.reviewapp.dto.auth;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.qnocks.reviewapp.dto.ReviewDto;

import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class JwtResponse {

    @NonNull
    private String accessToken;

    private String type = "Bearer";

    @NonNull
    private Long id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private List<String> roles;

    @NonNull
    private Set<ReviewDto> reviews;
}
