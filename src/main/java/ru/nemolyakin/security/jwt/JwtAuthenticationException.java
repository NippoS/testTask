package ru.nemolyakin.security.jwt;

public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException() {
        super();
    }

    public JwtAuthenticationException(String message) {
    }
}