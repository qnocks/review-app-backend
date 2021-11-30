package ru.qnocks.reviewapp.rest;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.qnocks.reviewapp.domain.Review;
import ru.qnocks.reviewapp.domain.Role;
import ru.qnocks.reviewapp.domain.User;
import ru.qnocks.reviewapp.dto.ReviewDto;
import ru.qnocks.reviewapp.dto.auth.JwtResponse;
import ru.qnocks.reviewapp.dto.auth.LoginRequest;
import ru.qnocks.reviewapp.dto.auth.MessageResponse;
import ru.qnocks.reviewapp.dto.auth.SignupRequest;
import ru.qnocks.reviewapp.repository.RoleRepository;
import ru.qnocks.reviewapp.repository.UserRepository;
import ru.qnocks.reviewapp.security.UserDetailsImpl;
import ru.qnocks.reviewapp.security.jwt.JwtUtils;
import ru.qnocks.reviewapp.service.DtoMapperService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthRestController {

    @NonNull
    private final AuthenticationManager authenticationManager;

    @NonNull
    private final JwtUtils jwtUtils;

    @NonNull
    private final DtoMapperService mapperService;

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final RoleRepository roleRepository;

    @NonNull
    private final PasswordEncoder encoder;

    private JwtResponse currentUser;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Set<Review> reviews = userDetails.getReviews();
        Set<ReviewDto> reviewDtos = reviews.stream()
                .map(mapperService::toDto)
                .collect(Collectors.toSet());

        currentUser = new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles,
                reviewDtos);

        return ResponseEntity.ok(currentUser);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName("ROLE_USER")
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<JwtResponse> getCurrentUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(IllegalAccessError::new);

        Set<Review> reviews = user.getReviews();
        Set<ReviewDto> reviewDtos = reviews.stream()
                .map(mapperService::toDto)
                .collect(Collectors.toSet());

        currentUser.setReviews(reviewDtos);

        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }
}
