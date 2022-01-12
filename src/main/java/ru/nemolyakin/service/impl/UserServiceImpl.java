package ru.nemolyakin.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nemolyakin.model.Role;
import ru.nemolyakin.model.User;
import ru.nemolyakin.model.UserStatus;
import ru.nemolyakin.repository.RoleRepository;
import ru.nemolyakin.repository.UserRepository;
import ru.nemolyakin.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User save(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        User registerUser = userRepository.save(user);

        log.info("IN UserServiceImpl save {}", user);

        return registerUser;
    }

    @Override
    public List findAll() {
        log.info("IN UserServiceImpl findAll");
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(new User());
        user.setId(id);
        return user;
    }


    @Override
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("User with id %d is not found", id));
        }
        User userDeleted = userRepository.findById(id).orElseThrow(IllegalStateException::new);

        userDeleted.setUserStatus(UserStatus.DELETED);
        userDeleted.setUpdated(LocalDateTime.now());
        userRepository.save(userDeleted);
        log.info("IN UserServiceImpl user with id {} is deleting", userDeleted.getId());
    }

    @Override
    public boolean isExists(Long id) {
        boolean result = userRepository.existsById(id);
        log.info("IN UserServiceImpl isExists - id {}, result {}", id, result);
        return result;
    }

    @Override
    public User update(User user) {
        if (user.getId() == null) {
            throw new NullPointerException("Id is required");
        }

        User userOld = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with that id %d is not found", user.getId())));

        user.setUpdated(LocalDateTime.now());
        user.setUserStatus(userOld.getUserStatus());
        User userUpdate = userRepository.save(user);

        log.info("IN UserServiceImpl update user {} was updated to user {}", userOld, userUpdate);

        return userUpdate;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }
}
