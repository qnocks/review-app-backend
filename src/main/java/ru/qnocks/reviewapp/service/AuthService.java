package ru.qnocks.reviewapp.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.qnocks.reviewapp.domain.Review;
import ru.qnocks.reviewapp.domain.Role;
import ru.qnocks.reviewapp.domain.User;
import ru.qnocks.reviewapp.dto.ReviewDto;
import ru.qnocks.reviewapp.dto.UserDto;
import ru.qnocks.reviewapp.dto.auth.JwtResponse;
import ru.qnocks.reviewapp.dto.auth.LoginRequest;
import ru.qnocks.reviewapp.dto.auth.MessageResponse;
import ru.qnocks.reviewapp.dto.auth.SignupRequest;
import ru.qnocks.reviewapp.security.UserDetailsImpl;
import ru.qnocks.reviewapp.security.jwt.JwtUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final DtoMapperService mapperService;

    private final UserService userService;

    private final RoleService roleService;

    private final PasswordEncoder encoder;

    private JwtResponse currentUser;

    public JwtResponse authenticate(LoginRequest loginRequest) {
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

        return currentUser;
    }

    public ResponseEntity<?> register(SignupRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        createUser(signUpRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private void createUser(SignupRequest signUpRequest) {
        UserDto user = new UserDto(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleService.findByName("ROLE_USER");
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    Role adminRole = roleService.findByName("ROLE_ADMIN");
                    roles.add(adminRole);
                } else {
                    Role userRole = roleService.findByName("ROLE_USER");
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        user.setActive(true);

        userService.create(user);
    }

    public JwtResponse refreshCurrentUser(UserDetailsImpl userDetails) {

        if (currentUser == null) {
            return null;
        }

        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(IllegalAccessError::new);

        Set<Review> reviews = user.getReviews();
        Set<ReviewDto> reviewDtos = reviews.stream()
                .map(mapperService::toDto)
                .collect(Collectors.toSet());

        currentUser.setReviews(reviewDtos);

        return currentUser;
    }
}
