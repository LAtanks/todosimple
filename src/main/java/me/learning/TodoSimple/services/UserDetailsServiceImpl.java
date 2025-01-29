package me.learning.TodoSimple.services;

import me.learning.TodoSimple.models.User;
import me.learning.TodoSimple.repositories.IUserRepository;
import me.learning.TodoSimple.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(
                username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username)
        );

        return new UserSpringSecurity(user, user.getProfiles());
    }



}
