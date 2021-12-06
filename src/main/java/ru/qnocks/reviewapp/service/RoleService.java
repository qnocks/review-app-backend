package ru.qnocks.reviewapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.qnocks.reviewapp.domain.Role;
import ru.qnocks.reviewapp.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }
}
