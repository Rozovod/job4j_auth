package ru.job4j.auth.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
@AllArgsConstructor
public class AuthExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(AuthExceptionHandler.class);
    private final ObjectMapper objectMapper;

    @ExceptionHandler(value = NullPointerException.class)
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {{
            put("message", "Логин или пароль не могут быть пустым полем");
            put("details", e.getMessage());
        }}));
        LOG.error(e.getMessage());
    }
}
