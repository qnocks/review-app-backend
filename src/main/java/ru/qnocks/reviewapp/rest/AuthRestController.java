package ru.qnocks.reviewapp.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.qnocks.reviewapp.dto.auth.JwtResponse;
import ru.qnocks.reviewapp.dto.auth.LoginRequest;
import ru.qnocks.reviewapp.dto.auth.SignupRequest;
import ru.qnocks.reviewapp.security.UserDetailsImpl;
import ru.qnocks.reviewapp.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "https://review-frontend-app.herokuapp.com"})
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return authService.register(signUpRequest);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<JwtResponse> getCurrentUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(authService.refreshCurrentUser(userDetails), HttpStatus.OK);
    }
}
