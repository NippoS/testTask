package ru.nemolyakin.service;

import ru.nemolyakin.model.Role;

public interface RoleService {
    Role findByName(String roleName);
}
