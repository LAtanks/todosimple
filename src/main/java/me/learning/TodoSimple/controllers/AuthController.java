package me.learning.TodoSimple.controllers;

import jakarta.validation.Valid;
import me.learning.TodoSimple.models.User;
import me.learning.TodoSimple.security.JWTUtil;
import me.learning.TodoSimple.security.UserSpringSecurity;
import me.learning.TodoSimple.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid User data) throws BadCredentialsException {
       UserSpringSecurity user = (UserSpringSecurity) this.userDetailsService.loadUserByUsername(data.getUsername());
/*
        var userCredetials = new UsernamePasswordAuthenticationToken( user.getUsername(), user.getPassword(), user.getAuthorities()) ;
        var auth = this.authenticationManager.authenticate(userCredetials);

        var token = jwtUtil.generateToken((User) auth.getPrincipal());
*/
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            data.getUsername(),
                            data.getPassword()
                    )
            );

            String token = jwtUtil.generateToken(data.getUsername());

            return ResponseEntity.ok().body(Map.of("token", token));
        }catch (BadCredentialsException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid Credencials"));
        }

       // return ResponseEntity.ok().body(token);
    }
}
