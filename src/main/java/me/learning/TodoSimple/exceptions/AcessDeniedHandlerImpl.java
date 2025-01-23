package me.learning.TodoSimple.exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@AllArgsConstructor
public class AcessDeniedHandlerImpl implements AccessDeniedHandler {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        request = this.request;
        response = this.response;
        accessDeniedException = new AccessDeniedException("Acesso não autorizado!");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write("Acesso não autorizado!");
    }
}
