package ru.nemolyakin.service;

import ru.nemolyakin.model.User;

public interface UserService extends GenericService <User, Long>{
    User findByUsername(String username);
}
