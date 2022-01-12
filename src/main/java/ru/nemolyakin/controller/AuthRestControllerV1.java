package ru.nemolyakin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nemolyakin.dto.AuthenticationDto;
import ru.nemolyakin.model.User;
import ru.nemolyakin.security.jwt.JwtTokenProvider;
import ru.nemolyakin.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/auth")
@Api(value = "Auth for admin")
public class AuthRestControllerV1 {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    @ApiOperation(value = "login for admin", response = Map.class)
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto requestDto){
        String username = requestDto.getUsername();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
        User user = userService.findByUsername(username);

        String token = jwtTokenProvider.createToken(user);

        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}
