package me.learning.TodoSimple.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthorizationException extends AccessDeniedException {

    public AuthorizationException(String file) {
        super(file);
    }
}
