package me.learning.TodoSimple.controllers;
import jakarta.validation.Valid;
import me.learning.TodoSimple.models.User;
import me.learning.TodoSimple.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid User data) {
        try {
            User user = new User();
            user.setUsername(data.getUsername());
            user.setPassword(data.getPassword());
            user.setId(data.getId());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
            return ResponseEntity.ok().body(Map.of("token", "Bearer "+jwtUtil.generateToken(data)));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }
    }
}
