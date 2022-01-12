package ru.nemolyakin.security.jwt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.nemolyakin.model.Role;
import ru.nemolyakin.model.User;
import ru.nemolyakin.model.UserStatus;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUserFactory {

    public static JwtUser createJwtUser(User user){
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                UserStatus.ACTIVE.equals(user.getUserStatus()),
                mapToGrantedAuthorities(user.getRoles())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles){
        return roles.stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

