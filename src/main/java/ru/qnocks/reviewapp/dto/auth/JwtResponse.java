package ru.qnocks.reviewapp.dto.auth;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class JwtResponse {

    @NonNull
    private String token;

    private String type = "Bearer";

    @NonNull
    private Long id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private List<String> roles;
}
