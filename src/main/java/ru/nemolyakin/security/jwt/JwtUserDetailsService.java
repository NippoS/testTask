package ru.nemolyakin.security.jwt;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nemolyakin.model.User;
import ru.nemolyakin.service.UserService;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userService.findByUsername(username);
            return JwtUserFactory.createJwtUser(user);
        } catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
