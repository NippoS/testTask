package ru.nemolyakin.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nemolyakin.model.Role;
import ru.nemolyakin.repository.RoleRepository;
import ru.nemolyakin.service.RoleService;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

}
